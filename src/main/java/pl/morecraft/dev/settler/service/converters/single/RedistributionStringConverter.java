package pl.morecraft.dev.settler.service.converters.single;


import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

@Component
@EntityConverter
public class RedistributionStringConverter extends AbstractConverter<Redistribution, String> {
    @Override
    protected String convert(Redistribution redistribution) {
        if (redistribution == null || redistribution.getUser() == null) {
            return null;
        }

        String value;
        PrivilegeObject object = redistribution.getParent();
        if (object != null && object instanceof Transaction) {
            value = String.format("%3.2f", 100. * redistribution.getValue() / ((Transaction) object).getValue()) + "%";
        } else {
            value = String.format("%3.2f", redistribution.getValue());
        }

        return redistribution.getUser().getFirstName()
                + " " + redistribution.getUser().getLastName()
                + " (" + value + ")";
    }
}
