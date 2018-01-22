package pl.morecraft.dev.settler;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import pl.morecraft.dev.settler.service.ExampleDataService;
import pl.morecraft.dev.settler.service.GraphSynchronizationService;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
})
public class ApplicationTest extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ApplicationTest.class)
                .headless(false)
                .run(args);

//        ExampleDataService exampleDataService = context.getBean(ExampleDataService.class);
//        exampleDataService.createExampleData();

        GraphSynchronizationService graphSynchronizationService = context.getBean(GraphSynchronizationService.class);
        graphSynchronizationService.doThings();
    }
}
