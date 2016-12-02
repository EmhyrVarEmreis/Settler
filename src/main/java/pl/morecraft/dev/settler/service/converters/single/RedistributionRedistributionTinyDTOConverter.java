package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;
import pl.morecraft.dev.settler.web.dto.RedistributionTinyDTO;

@Component
@EntityConverter
public class RedistributionRedistributionTinyDTOConverter extends AbstractConverter<Redistribution, RedistributionTinyDTO> {

    @Override
    protected RedistributionTinyDTO convert(Redistribution redistribution) {
        return new RedistributionTinyDTO(
                redistribution.getId().getUser().getFirstName() + " " + redistribution.getId().getUser().getLastName(),
                redistribution.getValue()
        );
    }

}
