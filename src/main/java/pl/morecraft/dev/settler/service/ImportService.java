package pl.morecraft.dev.settler.service;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.morecraft.dev.settler.dao.repository.TransactionRepository;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.RedistributionType;
import pl.morecraft.dev.settler.domain.dictionaries.TransactionType;
import pl.morecraft.dev.settler.security.util.Security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImportService {

    private static Logger log = LoggerFactory.getLogger(ImportService.class);

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final Gson gson;

    @Autowired
    public ImportService(
            @Qualifier("prettyGson") Gson gson,
            TransactionRepository transactionRepository,
            UserRepository userRepository) {
        this.gson = gson;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public void importTransactions(MultipartFile file) throws IOException {

        List<Transaction> transactionList = new ArrayList<>();

        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String content = IOUtils.toString(stream, "UTF-8");

        String[] lines = content.split("\\r?\\n");

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");
        DateTimeFormatter dateTimeFormatterReverse = DateTimeFormat.forPattern("yyyyMMdd");

        User o = userRepository.findOneByLogin(splitLine(lines[0])[26]);
        User c = userRepository.findOneByLogin(splitLine(lines[0])[27]);

        int transactionCounter = 0;
        int ln = 0;

        for (String line : lines) {
            ln++;
            if (ln < 3) {
                continue;
            }
            try {
                String[] cells = splitLine(line);
                String name = cells[0];
                String mark = cells[4].trim();

                Double value;
                Double sharedAValue;
                Double sharedBValue;
                Integer sharedA;
                Integer sharedB;
                LocalDateTime date;
                Boolean normal;

                Transaction transaction = new Transaction();
                List<Redistribution> owners = new ArrayList<>();
                List<Redistribution> contractors = new ArrayList<>();
                value = Double.valueOf(cells[2].replaceAll("[^0-9^,-]+", "").replace(",", "."));
                date = dateTimeFormatter.parseLocalDateTime(cells[1]);
                sharedA = cells.length < 7 || cells[5].trim().isEmpty() ? -1 : Integer.valueOf(cells[5].trim());
                sharedB = cells.length < 7 || cells[6].trim().isEmpty() ? -1 : Integer.valueOf(cells[6].trim());

                normal = value > 0;

                value = Math.abs(value);

                if (mark.equalsIgnoreCase("x")) {
                    if (sharedA < 0 || sharedB < 0) {
                        sharedA = 1;
                        sharedB = 2;
                    }
                } else if (mark.equalsIgnoreCase("b") || mark.equalsIgnoreCase("z")) {
                    sharedA = 1;
                    sharedB = 1;
                } else {
                    continue;
                }

                sharedAValue = value;
                value = (value / (sharedA)) * sharedB;
                value = (double) Math.round(value * 100) / 100;
                sharedBValue = value - sharedAValue;
                sharedBValue = (double) Math.round(sharedBValue * 100) / 100;

                if (normal) {
                    owners.add(new Redistribution(RedistributionType.O, transaction, o, value));
                    if (sharedBValue > 0) {
                        contractors.add(new Redistribution(RedistributionType.C, transaction, o, sharedBValue));
                    }
                    contractors.add(new Redistribution(RedistributionType.C, transaction, c, sharedAValue));
                } else {
                    owners.add(new Redistribution(RedistributionType.O, transaction, c, value));
                    contractors.add(new Redistribution(RedistributionType.C, transaction, o, sharedAValue));
                    if (sharedBValue > 0) {
                        contractors.add(new Redistribution(RedistributionType.C, transaction, c, sharedBValue));
                    }
                }

                transaction.setOwners(owners);
                transaction.setContractors(contractors);

                date = date.withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0);

                transaction.setCreator(Security.currentUser());
                transaction.setType(TransactionType.NOR);
                transaction.setDescription(name);
                transaction.setValue(value);
                //transaction.setCreated(new LocalDateTime());
                transaction.setEvaluated(date);
                transaction.setReference("T" + date.toString(dateTimeFormatterReverse) + String.format("%06d", transactionCounter));
                transactionList.add(transaction);
                transactionCounter++;
            } catch (Exception e) {
                log.warn("Unable to convert line No{}: {}", ln, line, e);
            }
        }

        transactionList.sort(
                (o1, o2) -> o1.getEvaluated().compareTo(o2.getCreated())
        );

        transactionList.forEach(
                t -> {
                    t.setCreated(new LocalDateTime());
                    log.info(t.getEvaluated()
                            + " " + t.getReference()
                            + " " + t.getValue()
                            + " [" + t.getOwners().stream().map(r -> r.getUser().getLogin() + "/" + r.getValue()).collect(Collectors.joining(", ")) + "]"
                            + " [" + t.getContractors().stream().map(r -> r.getUser().getLogin() + "/" + r.getValue()).collect(Collectors.joining(", ")) + "]"
                    );
                    transactionRepository.save(t);
                }
        );

    }

    private String[] splitLine(String line) {
        return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }

}
