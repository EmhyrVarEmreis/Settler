package pl.morecraft.dev.settler.service.converters;

import org.modelmapper.AbstractConverter;

import java.util.List;

public class ListIntegerConverter extends AbstractConverter<List<?>, Integer> {
    @Override
    protected Integer convert(List<?> list) {
        return list == null ? null : list.size();
    }
}