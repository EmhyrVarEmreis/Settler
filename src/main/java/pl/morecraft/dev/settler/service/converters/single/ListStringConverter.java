package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

import java.util.List;
import java.util.stream.Collectors;

@Component
@EntityConverter
public class ListStringConverter extends AbstractConverter<List<?>, String> {

    @Override
    protected String convert(List<?> list) {
        return list == null ? null : list.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

}