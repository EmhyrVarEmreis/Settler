package pl.morecraft.dev.settler.service.converters.prototype;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.List;

public interface EntityConvertersPack {

    List<AbstractConverter> getFullEntityConvertersPack();

    ModelMapper getPreparedModelMapper();

}
