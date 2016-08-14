package pl.morecraft.dev.settler.web.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class JsonJodaSerializerFix extends JsonSerializer<LocalDateTime> {
    //JsonJodaSerializerFix

    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator generator, SerializerProvider provider) throws IOException {
        String dateString = localDateTime.toString(formatter);
        generator.writeString(dateString);
    }

}
