package pl.morecraft.dev.settler.service.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConvertersPack;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListMapper {

    private final EntityConvertersPack entityConvertersPack;

    @Autowired
    public ListMapper(EntityConvertersPack entityConvertersPack) {
        this.entityConvertersPack = entityConvertersPack;
    }

    public <S, T> List<T> map(List<S> source, Class<T> destinationClass) {
        List<T> ret = new ArrayList<>();
        final ModelMapper preparedModelMapper = entityConvertersPack.getPreparedModelMapper();
        source.forEach(
                obj -> ret.add(preparedModelMapper.map(obj, destinationClass))
        );
        return ret;
    }

}