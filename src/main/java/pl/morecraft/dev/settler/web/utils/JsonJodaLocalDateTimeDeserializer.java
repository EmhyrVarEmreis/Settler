package pl.morecraft.dev.settler.web.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class JsonJodaLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static DateTimeFormatter formatterWithTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (Objects.equals(jp.getText(), "")) {
            return null;
        }
        try {
            return LocalDateTime.parse(jp.getText(), formatterWithTime);
        } catch (IllegalArgumentException e) {
            LoggerFactory.getLogger(this.getClass()).error("", e);
            LocalDate localDate = LocalDate.parse(jp.getText(), formatter);
            return new LocalDateTime(
                    localDate.getYear(),
                    localDate.getMonthOfYear(),
                    localDate.getDayOfMonth(),
                    0,
                    0,
                    0
            );
        }
    }

}

