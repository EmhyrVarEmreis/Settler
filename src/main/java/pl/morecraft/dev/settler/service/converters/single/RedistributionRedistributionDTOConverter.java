package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;
import pl.morecraft.dev.settler.web.dto.RedistributionDTO;
import pl.morecraft.dev.settler.web.dto.UserTinyDTO;

@Component
@EntityConverter
public class RedistributionRedistributionDTOConverter extends AbstractConverter<Redistribution, RedistributionDTO> {

    @Override
    protected RedistributionDTO convert(Redistribution redistribution) {
        return new RedistributionDTO(
                redistribution.getId().getType().getCode(),
                redistribution.getId().getParent().getId(),
                new UserTinyDTO(
                        redistribution.getId().getUser().getId(),
                        redistribution.getId().getUser().getLogin(),
                        redistribution.getId().getUser().getFirstName(),
                        redistribution.getId().getUser().getLastName(),
                        redistribution.getId().getUser().getEmail()
                ),
                redistribution.getValue()
        );
    }

}
