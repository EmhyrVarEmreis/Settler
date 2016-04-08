package pl.morecraft.dev.settler.service.converters.single;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

@Component
@EntityConverter
public class JodaLocalDateTimeLocalDateConverter extends AbstractConverter<LocalDateTime, LocalDate> {
    @Override
    protected LocalDate convert(LocalDateTime localDateTime) {
        return localDateTime == null ? null : new LocalDate(localDateTime);
    }
}
