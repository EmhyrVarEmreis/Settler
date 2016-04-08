package pl.morecraft.dev.settler.web.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.Objects;

public class JsonJodaLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static DateTimeFormatter formatterWithTime = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss");

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (Objects.equals(jp.getText(), "")) {
            return null;
        }
        try {
            return LocalDate.parse(jp.getText(), formatter);
        } catch (IllegalArgumentException e) {
            return new LocalDate(LocalDateTime.parse(jp.getText(), formatterWithTime));
        }
    }

}

