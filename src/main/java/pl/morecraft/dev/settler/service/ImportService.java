package pl.morecraft.dev.settler.service;

import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.morecraft.dev.settler.domain.Transaction;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ImportService {

    private static Logger log = LoggerFactory.getLogger(ImportService.class);

    public void importTransactions(MultipartFile file) throws IOException {

        List<Transaction> transactionList = new ArrayList<>();

        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String content = IOUtils.toString(stream, "UTF-8");

        String[] lines = content.split("\\n");

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");
        DateTimeFormatter dateTimeFormatterReverse = DateTimeFormat.forPattern("yyyyMMdd");

        int transactionCounter = 0;

        for (String line : lines) {
            String[] cells = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            //System.out.println(cells[0] + "|" + cells[1] + "|" + cells[2]);
            String name = cells[0];
            String mark = cells[4].trim();
            String shared = cells[5];
            Double value;
            LocalDateTime date;
            try {
                value = Double.valueOf(cells[2].replaceAll("[^0-9^,-]+", "").replace(",", "."));
                date = dateTimeFormatter.parseLocalDateTime(cells[1]);
            } catch (IllegalArgumentException e) {
                log.warn("Unable to convert: " + line, e);
                continue;
            }
            if (mark.equalsIgnoreCase("x")) {
                value *= 2;
            }
            Transaction transaction = new Transaction();
            transaction.setValue(value);
            transaction.setCreated(new LocalDateTime());
            transaction.setEvaluated(date);
            transaction.setReference("T" + date.toString(dateTimeFormatterReverse) + String.format("%06d", transactionCounter));
            transactionList.add(transaction);
            transactionCounter++;
        }

        transactionList.forEach(
                System.out::println
        );

    }

}
