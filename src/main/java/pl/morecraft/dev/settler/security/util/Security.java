package pl.morecraft.dev.settler.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.morecraft.dev.settler.domain.User;
import pl.morecraft.dev.settler.security.UserDetailsWrapper;

import java.util.Optional;

/**
 * Provides security utils.
 */
public class Security {

    private Security() {
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static User currentUser() {
        Optional<SecurityContext> context = Optional.ofNullable(SecurityContextHolder.getContext());
        User user = context
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(UserDetailsWrapper.class::cast)
                .map(UserDetailsWrapper::getUser)
                .orElse(null);
        return user;
    }

}
