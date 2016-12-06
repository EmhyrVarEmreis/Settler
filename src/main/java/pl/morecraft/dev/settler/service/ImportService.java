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
import pl.morecraft.dev.settler.dao.repository.CategoryRepository;
import pl.morecraft.dev.settler.dao.repository.TransactionRepository;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.Category;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.domain.dictionaries.RedistributionType;
import pl.morecraft.dev.settler.domain.dictionaries.TransactionType;
import pl.morecraft.dev.settler.security.util.Security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImportService {

    private static Logger log = LoggerFactory.getLogger(ImportService.class);

    private final UserRepository userRepository;
    private final SequenceManager sequenceManager;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final Gson gson;

    @Autowired
    public ImportService(
            @Qualifier("prettyGson") Gson gson,
            UserRepository userRepository,
            SequenceManager sequenceManager,
            CategoryRepository categoryRepository,
            TransactionRepository transactionRepository
    ) {
        this.gson = gson;
        this.userRepository = userRepository;
        this.sequenceManager = sequenceManager;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public void importTransactions(MultipartFile file) throws IOException {

        List<Category> categoryList = categoryRepository.findAll();
        Map<String, Category> categoryMap = categoryList == null ? new HashMap<>() : categoryList.stream().collect(
                Collectors.toMap(
                        Category::getCode,
                        category -> category
                )
        );

        List<Transaction> transactionList = new ArrayList<>();

        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String content = IOUtils.toString(stream, "UTF-8");

        String[] lines = content.split("\\r?\\n");

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");

        User o = userRepository.findOneByLogin(splitLine(lines[0])[26]);
        User c = userRepository.findOneByLogin(splitLine(lines[0])[27]);

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
                transaction.setEvaluated(date);

                List<Category> cl = checkCategories(transaction.getDescription()).stream().map(
                        categoryMap::get
                ).filter(
                        Objects::nonNull
                ).collect(Collectors.toList());

                transaction.setCategories(
                        cl.isEmpty() ? null : cl
                );

                transaction.setReference(sequenceManager.getNextReferenceForTransaction(transaction));

                transactionList.add(transaction);
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
                            + " [" + t.getDescription() + "]"
                            + " [" + t.getOwners().stream().map(r -> r.getId().getUser().getLogin() + "/" + r.getValue()).collect(Collectors.joining(", ")) + "]"
                            + " [" + t.getContractors().stream().map(r -> r.getId().getUser().getLogin() + "/" + r.getValue()).collect(Collectors.joining(", ")) + "]"
                            + " [" + (t.getCategories() == null ? "" : t.getCategories().stream().map(Category::getCode).collect(Collectors.joining(", "))) + "]"
                    );
                    transactionRepository.save(t);
                }
        );

    }

    private List<String> checkCategories(String description) {
        List<String> categories = new ArrayList<>();
        description = description.toLowerCase();
        if (description.contains("internet")
                || description.contains("serwer")
                || description.contains("server")) {
            categories.add("INF");
        }
        if (description.contains("rofist")
                || description.contains("piwa")
                || description.contains("binh")
                || description.contains("pizza")
                || description.contains("kfc")
                || description.contains("na dowóz")
                || description.contains("chińczyk")
                || description.contains("bierhalle")
                || description.contains("piwer")
                || description.contains("wołowina")
                || description.contains("cielęcina")
                || description.contains("dzong")
                || description.contains("parnik")
                || description.contains("sapaya")
                || description.contains("pho")
                || description.contains("domin")
                || description.contains("burger")
                || description.contains("wód")
                || description.contains("restauracja")
                || description.contains("tortilla")
                || description.contains("kebab")
                || description.contains("kebeb")
                || description.contains("mi-ha")
                || description.contains("choya")
                || description.contains("cydr")
                || description.contains("piwo")
                || description.contains("szwejk")
                || description.contains("locome")
                || description.contains("barkle")
                || description.contains("chiny")
                || description.contains("micha")
                || description.contains("pałaszowanie")
                || description.contains("poker")
                || description.contains("saigon")
                || description.contains("frytki")
                || description.contains("batumi")
                || description.contains("huong")
                || description.contains("sajgonka")
                || description.contains("mekong")
                || description.contains("lemonka")
                || description.contains("loco")
                || description.contains("bydło")
                || description.contains("olej")
                || description.contains("american food")
                || description.contains("green cafe")
                || description.contains("open air")
                || description.contains("lua wang")
                || description.contains("ketchup")
                || description.contains("chleb")) {
            categories.add("FFD");
        }
        if (description.contains("pożyczka")
                || description.contains("kaszanka")
                || description.contains("zwrot")) {
            categories.add("FNC");
        }
        if (description.contains("starwars")
                || description.contains("spotkanie")
                || description.contains("ogame")
                || description.contains("cygar")
                || description.contains("rofist")
                || description.contains("netflix")
                || description.contains("kino")
                || description.contains("restauracja")) {
            categories.add("ENT");
        }
        if (description.contains("bilet")
                || description.contains("taxi")
                || description.contains("pkp")) {
            categories.add("TRN");
        }
        if (description.contains("zalman")
                || description.contains("domofon")
                || description.contains("pływak")
                || description.contains("filtry")
                || description.contains("worki")
                || description.contains("klucz")
                || description.contains("silentium")
                || description.contains("matbud")) {
            categories.add("THG");
        }
        if (description.contains("rwe")
                || description.contains("pgnig")
                || description.contains("woda")
                || description.contains("opłaty")) {
            categories.add("BLS");
        }
        return categories;
    }

    private String[] splitLine(String line) {
        return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }

}
