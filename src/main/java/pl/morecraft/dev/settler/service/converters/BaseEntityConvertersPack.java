package pl.morecraft.dev.settler.service.converters;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConvertersPack;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class BaseEntityConvertersPack implements EntityConvertersPack {

    private List<AbstractConverter> abstractConverterList;

    @Inject
    public BaseEntityConvertersPack(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EntityConverter.class);
        abstractConverterList = new ArrayList<>(beans.size());
        for (Object o : beans.values()) {
            if (o instanceof AbstractConverter) {
                abstractConverterList.add((AbstractConverter) o);
            } else {
                throw new ClassCastException("Unable to cast " + o.getClass() + " to AbstractConverter");
            }
        }
    }

    @Override
    public ModelMapper getPreparedModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        getFullEntityConvertersPack().forEach(
                modelMapper::addConverter
        );
        return modelMapper;
    }

    @Override
    public synchronized List<AbstractConverter> getFullEntityConvertersPack() {
        return abstractConverterList;
    }

}
