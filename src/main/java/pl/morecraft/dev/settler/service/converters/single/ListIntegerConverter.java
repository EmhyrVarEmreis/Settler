package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

import java.util.List;

@Component
@EntityConverter
public class ListIntegerConverter extends AbstractConverter<List<?>, Integer> {
    @Override
    protected Integer convert(List<?> list) {
        return list == null ? null : list.size();
    }
}