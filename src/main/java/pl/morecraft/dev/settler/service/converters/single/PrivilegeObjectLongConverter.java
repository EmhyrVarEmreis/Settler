package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.PrivilegeObject;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

@Component
@EntityConverter
public class PrivilegeObjectLongConverter extends AbstractConverter<PrivilegeObject, Long> {

    @Override
    protected Long convert(PrivilegeObject privilegeObject) {
        return privilegeObject == null ? null : privilegeObject.getId();
    }

}
