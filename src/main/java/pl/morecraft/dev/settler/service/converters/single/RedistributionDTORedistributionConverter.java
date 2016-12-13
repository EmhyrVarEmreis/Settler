package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.dao.repository.UserRepository;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.domain.Redistribution;
import pl.morecraft.dev.settler.domain.dictionaries.RedistributionType;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;
import pl.morecraft.dev.settler.web.dto.RedistributionDTO;

@Component
@EntityConverter
public class RedistributionDTORedistributionConverter extends AbstractConverter<RedistributionDTO, Redistribution> {

    private final UserRepository userRepository;

    @Autowired
    public RedistributionDTORedistributionConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected Redistribution convert(RedistributionDTO redistributionDTO) {
        return new Redistribution(
                redistributionDTO.getType() == null ? null : RedistributionType.valueOf(redistributionDTO.getType()),
                PrivilegeObject.from(redistributionDTO.getParent()),
                userRepository.findOne(redistributionDTO.getUser().getId()),
                redistributionDTO.getPercentage()
        );
    }

}
