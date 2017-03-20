package pl.morecraft.dev.settler.service.social;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.security.exception.FacebookLoginException;

import java.util.Objects;

@Service
public class FacebookService {

    public Facebook getFacebook(String authToken) {
        Facebook facebook = new FacebookTemplate(authToken);
        if (!facebook.isAuthorized()) {
            throw new FacebookLoginException("Cannot authorize app");
        }
        return facebook;
    }

    public Long authTokenGetUserId(String authToken) {
        String id = getFacebook(authToken)
                .fetchObject("me", org.springframework.social.facebook.api.User.class, "id")
                .getId();
        if (Objects.isNull(id)) {
            throw new FacebookLoginException("Retrieved null id");
        }
        return Long.parseLong(
                id
        );
    }

    public boolean authTokenVerifyUserId(String authToken, Long id) {
        return authTokenGetUserId(authToken).equals(id);
    }

}
