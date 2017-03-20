package pl.morecraft.dev.settler.security.exception;


import org.springframework.security.core.AuthenticationException;

public class UserBlockedException extends AuthenticationException {

    public UserBlockedException(String message) {
        super(message);
    }

    public UserBlockedException(String message, Throwable t) {
        super(message, t);
    }

}
