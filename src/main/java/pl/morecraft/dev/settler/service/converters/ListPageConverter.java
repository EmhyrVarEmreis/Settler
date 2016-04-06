package pl.morecraft.dev.settler.service.converters;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConvertersPack;
import pl.morecraft.dev.settler.service.exception.InvalidPageException;
import pl.morecraft.dev.settler.web.misc.ListPage;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListPageConverter {

    @Inject
    private EntityConvertersPack entityConvertersPack;

    public <S, T> ListPage<T> convert(Page<S> page, Class<T> tClass) {

        if (page == null) {
            throw new InvalidPageException("ListPage is null");
        }

        ListPage<T> result = new ListPage<>();
        List<T> content = new ArrayList<>();
        ModelMapper modelMapper = entityConvertersPack.getPreparedModelMapper();

        result.setTotal(page.getTotalElements());

        for (S s : page.getContent()) {
            content.add(modelMapper.map(s, tClass));
        }

        result.setContent(content);

        return result;
    }

}