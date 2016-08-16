package pl.morecraft.dev.settler.service;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.morecraft.dev.settler.dao.repository.SettlementRepository;
import pl.morecraft.dev.settler.dao.repository.TransactionRepository;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.RedistributionType;
import pl.morecraft.dev.settler.domain.dictionaries.TransactionType;
import pl.morecraft.dev.settler.security.util.Security;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ImportService {

    private static Logger log = LoggerFactory.getLogger(ImportService.class);
    @Inject
    private UserRepository userRepository;
    @Inject
    private SettlementRepository settlementRepository;
    @Inject
    private TransactionRepository transactionRepository;
    @Inject
    @Qualifier("prettyGson")
    private Gson gson;

    public void importTransactions(MultipartFile file) throws IOException {

        List<Transaction> transactionList = new ArrayList<>();

        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String content = IOUtils.toString(stream, "UTF-8");

        String[] lines = content.split("\\r?\\n");

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");
        DateTimeFormatter dateTimeFormatterReverse = DateTimeFormat.forPattern("yyyyMMdd");
//
//        User admin = userRepository.findOneByLogin("admin");
//
//        Transaction t = new Transaction();
//        t.setDescription("DESC");
//        t.setCreated(new LocalDateTime());
//        t.setCreator(admin);
//        t.setType(TransactionType.NOR);
//        t.setValue(22.0);
//        t.setReference((new LocalDateTime()).toString(dateTimeFormatterReverse));
//        List<Redistribution> redistributionList = new ArrayList<>();
//        redistributionList.add(new Redistribution(RedistributionType.O, t, admin, 22.0));
//        t.setContractors(redistributionList);
//        transactionRepository.save(t);
//
//        if (true) {
//            return;
//        }

        User o = userRepository.findOneByLogin(splitLine(lines[0])[26]);
        User c = userRepository.findOneByLogin(splitLine(lines[0])[27]);
        User one, two;

        int transactionCounter = 500;

        for (String line : lines) {
            String[] cells = splitLine(line);
            //System.out.println(cells[0] + "|" + cells[1] + "|" + cells[2]);
            String name = cells[0];
            String mark = cells[4].trim();
            Double value;
            Integer shared;
            LocalDateTime date;

            Transaction transaction = new Transaction();
            List<Redistribution> owners = new ArrayList<>();
            List<Redistribution> contractors = new ArrayList<>();

            try {
                value = Double.valueOf(cells[2].replaceAll("[^0-9^,-]+", "").replace(",", "."));
                date = dateTimeFormatter.parseLocalDateTime(cells[1]);
                shared = cells.length < 6 || cells[5].trim().isEmpty() ? -1 : Integer.valueOf(cells[5].trim());
            } catch (Exception e) {
                log.warn("Unable to convert: " + line, e);
                continue;
            }
            if (mark.equalsIgnoreCase("z")) {
                continue;
            } else if (mark.equalsIgnoreCase("x")) {
                value *= 2;
            } else if (shared > 0) {
                value *= shared;
                continue;
            }

            if (value > 0) {
                one = o;
                two = c;
            } else {
                one = c;
                two = o;
            }

            owners.add(new Redistribution(RedistributionType.O, transaction, one, 50.));
            contractors.add(new Redistribution(RedistributionType.C, transaction, two, 50.));

            transaction.setOwners(owners);
            transaction.setContractors(contractors);

            transaction.setCreator(Security.currentUser());
            transaction.setType(TransactionType.NOR);
            transaction.setDescription(name);
            transaction.setValue(Math.abs(value));
            transaction.setCreated(new LocalDateTime());
            transaction.setEvaluated(date);
            transaction.setReference("T" + date.toString(dateTimeFormatterReverse) + String.format("%06d", transactionCounter));
            transactionList.add(transaction);
            transactionCounter++;
        }

        transactionRepository.save(transactionList);

    }

    private String[] splitLine(String line) {
        return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }

}
