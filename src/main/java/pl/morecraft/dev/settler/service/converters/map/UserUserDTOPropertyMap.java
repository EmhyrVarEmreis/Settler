package pl.morecraft.dev.settler.service.converters.map;

import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverterPropertyMap;
import pl.morecraft.dev.settler.web.dto.UserDTO;

@Component
@EntityConverterPropertyMap
public class UserUserDTOPropertyMap extends PropertyMap<User, UserDTO> {

    protected void configure() {
        map().setAvatar(source.getAvatarId());
    }

}
