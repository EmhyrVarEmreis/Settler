package pl.morecraft.dev.settler.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns a 401 error code (Unauthorized) to the client.
 */
@Component
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(Http401UnauthorizedEntryPoint.class);

    /**
     * Always returns a 401 error code to the client.
     */
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException,
            ServletException {

        log.debug("Pre-authenticated entry point called. Rejecting access");

        if (exception.getCause() instanceof UserNotActivatedException) {
            response.sendError(600, "User not Activated");
        } else if (exception.getCause() instanceof UserBlockedException) {
            response.sendError(601, "User Blocked");
        } else if (exception.getCause() instanceof PasswordExpiredException) {
            response.sendError(602, "User's Password Expired");
        } else if (exception.getCause() instanceof UserSuspendedException) {
            response.sendError(603, "User Suspended");
        } else if (exception.getCause() instanceof AccountExpiredException) {
            response.sendError(604, "Account Expired");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
        }

    }

}
