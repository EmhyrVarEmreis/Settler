package pl.morecraft.dev.settler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"pl.morecraft.dev.settler"}, exclude = {
        DataSourceAutoConfiguration.class,
})
public class Application {

    private static final Class<Application> APPLICATION_CLASS = Application.class;

    public static void main(final String[] args) {
        SpringApplication.run(APPLICATION_CLASS, args);
    }

}
