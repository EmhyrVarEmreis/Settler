package pl.morecraft.dev.settler.service.converters;


import org.modelmapper.AbstractConverter;
import pl.morecraft.dev.settler.domain.User;

public class UserStringConverter extends AbstractConverter<User, String> {
    //konwersja user na typ stringa. Jednego typu na drugi, aby pasowały
    @Override
    protected String convert(User user) {
        return user.getFirstName() + " " + user.getLastName() + " (" + user.getLogin() + ")";
    }
}