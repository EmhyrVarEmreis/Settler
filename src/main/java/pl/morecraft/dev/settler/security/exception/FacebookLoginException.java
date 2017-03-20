package pl.morecraft.dev.settler.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Could not login using FB")
public class FacebookLoginException extends RuntimeException {

    public FacebookLoginException(String message) {
        super(message);
    }

    public FacebookLoginException(String message, Throwable t) {
        super(message, t);
    }

}
