package pl.morecraft.dev.settler.web.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class JsonDoubleDeserializer extends JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String s = jsonParser.getText().replace(" ", "").replace(",", ".");
        Double d = Double.valueOf(s.length() > 0 ? s : "0.00");
        return d == 0.00 ? null : d;
    }
}
