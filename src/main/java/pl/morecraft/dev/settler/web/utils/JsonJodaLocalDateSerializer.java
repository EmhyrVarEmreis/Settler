package pl.morecraft.dev.settler.web.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class JsonJodaLocalDateSerializer extends JsonSerializer<LocalDate> {

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate localDate, JsonGenerator generator, SerializerProvider provider) throws IOException {
        String dateString = localDate.toString(formatter);
        generator.writeString(dateString);
    }

}
