package pl.morecraft.dev.settler.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "settler", ignoreUnknownFields = false)
@Getter
public class SettlerProperties {

    private final Async async = new Async();
    private final MailConfiguration mail = new MailConfiguration();

    @Data
    @NoArgsConstructor
    public static class Async {
        private int corePoolSize = 2;
        private int maxPoolSize = 48;
        private int queueCapacity = 10240;
    }

    @Data
    @NoArgsConstructor
    public class MailConfiguration {
        private String fromAddress;
        private String fromTitle;
    }

}
