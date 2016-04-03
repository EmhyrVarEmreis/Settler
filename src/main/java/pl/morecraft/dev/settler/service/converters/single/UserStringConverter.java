package pl.morecraft.dev.settler.service.converters.single;


import org.modelmapper.AbstractConverter;
import pl.morecraft.dev.settler.domain.User;

public class UserStringConverter extends AbstractConverter<User, String> {
    @Override
    protected String convert(User user) {
        return user.getFirstName() + " " + user.getLastName() + " (" + user.getLogin() + ")";
    }
}