package pl.morecraft.dev.settler.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.morecraft.dev.settler.security.xauth.TokenProvider;

/**
 * Configures x-auth-token security.
 */
@Configuration
public class XAuthConfiguration {

    @Value("${token.secret}")
    private String secret;
    @Value("${token.validityInSeconds}")
    private int validityInSeconds;

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider(secret, validityInSeconds);
    }

}
