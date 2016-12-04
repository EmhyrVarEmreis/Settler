package pl.morecraft.dev.settler.web.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class JsonDoubleCurrencySerializer extends JsonSerializer<Double> {

    @Override
    public void serialize(Double d, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (d == null || d == 0.00) {
            jsonGenerator.writeObject(null);
        } else {
            jsonGenerator.writeString(getFormat().format(d));
        }
    }

    public static DecimalFormat getFormat() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        DecimalFormat numFormat = new DecimalFormat();
        numFormat.setGroupingUsed(true);
        numFormat.setMinimumFractionDigits(2);
        numFormat.setMaximumFractionDigits(2);
        numFormat.setDecimalFormatSymbols(symbols);
        return numFormat;
    }

}
