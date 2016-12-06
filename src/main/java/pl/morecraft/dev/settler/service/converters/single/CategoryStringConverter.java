package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.Category;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

@Component
@EntityConverter
public class CategoryStringConverter extends AbstractConverter<Category, String> {

    @Override
    protected String convert(Category category) {
        return category.getCode();
    }

}
