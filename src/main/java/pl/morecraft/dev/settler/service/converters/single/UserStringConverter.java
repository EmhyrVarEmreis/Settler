package pl.morecraft.dev.settler.service.converters.single;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.service.converters.prototype.EntityConverter;

@Component
@EntityConverter
public class UserStringConverter extends AbstractConverter<User, String> {
    @Override
    protected String convert(User user) {
        if (user == null) {
            return null;
        }
        return user.getFirstName() + " " + user.getLastName() + " (" + user.getLogin() + ")";
    }
}