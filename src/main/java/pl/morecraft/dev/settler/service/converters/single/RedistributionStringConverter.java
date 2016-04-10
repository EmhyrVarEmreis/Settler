package pl.morecraft.dev.settler.service.converters.single;


import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

@Component
@EntityConverter
public class RedistributionStringConverter extends AbstractConverter<Redistribution, String> {
    @Override
    protected String convert(Redistribution redistribution) {
        if (redistribution == null || redistribution.getUser() == null) {
            return null;
        }
        return redistribution.getUser().getFirstName()
                + " " + redistribution.getUser().getLastName()
                + " (" + String.format("%3.2f", redistribution.getPercentage()) + "%)";
    }
}
