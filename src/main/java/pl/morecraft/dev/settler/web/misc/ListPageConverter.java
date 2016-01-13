package pl.morecraft.dev.settler.web.misc;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import pl.morecraft.dev.settler.service.converters.UserStringConverter;
import pl.morecraft.dev.settler.service.exception.InvalidPageException;

import java.util.ArrayList;
import java.util.List;

public class ListPageConverter<S, T> {

    public ListPage<T> convert(Page<S> page, Class<T> tClass) {

        if (page == null) {
            throw new InvalidPageException("ListPage is null");
        }

        ListPage<T> result = new ListPage<>();
        List<T> content = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(new UserStringConverter());

        result.setTotal(page.getTotalElements());

        for (S s : page.getContent()) {
            content.add(modelMapper.map(s, tClass));
        }

        result.setContent(content);

        return result;
    }

}