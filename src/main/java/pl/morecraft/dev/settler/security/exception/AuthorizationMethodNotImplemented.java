package pl.morecraft.dev.settler.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Authorization method not implemented")
public class AuthorizationMethodNotImplemented extends RuntimeException {

    public AuthorizationMethodNotImplemented(String message) {
        super(message);
    }

    public AuthorizationMethodNotImplemented(String message, Throwable t) {
        super(message, t);
    }

}
