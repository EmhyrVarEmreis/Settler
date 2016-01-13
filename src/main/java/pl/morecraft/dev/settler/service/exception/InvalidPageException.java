package pl.morecraft.dev.settler.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid filters")
public class InvalidPageException extends RuntimeException {

    public InvalidPageException(String message) {
        super(message);
    }

    public InvalidPageException(String message, Throwable t) {
        super(message, t);
    }

}
