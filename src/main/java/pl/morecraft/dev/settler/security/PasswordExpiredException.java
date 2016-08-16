package pl.morecraft.dev.settler.security;

import org.springframework.security.core.AuthenticationException;

public class PasswordExpiredException extends AuthenticationException {

    public PasswordExpiredException(String message) {
        super(message);
    }

    public PasswordExpiredException(String message, Throwable t) {
        super(message, t);
    }

}