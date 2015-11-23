package pl.morecraft.dev.settler.security;

import org.springframework.security.core.AuthenticationException;

public class AccountExpiredException extends AuthenticationException {

    public AccountExpiredException(String message) {
        super(message);
    }

    public AccountExpiredException(String message, Throwable t) {
        super(message, t);
    }
}