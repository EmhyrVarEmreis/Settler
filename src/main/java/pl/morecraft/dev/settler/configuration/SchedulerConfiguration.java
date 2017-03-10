package pl.morecraft.dev.settler.configuration;

import lombok.extern.slf4j.Slf4j;
import org.quartz.spi.JobFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import pl.morecraft.dev.settler.jobs.AutowiringSpringBeanJobFactory;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class SchedulerConfiguration {

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) {
        log.debug("Creating SchedulerFactoryBean");
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setSchedulerName("defaultScheduler");
        factoryBean.setStartupDelay(15);
        factoryBean.setWaitForJobsToCompleteOnShutdown(true);
        factoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));

        factoryBean.setJobFactory(jobFactory);
        factoryBean.setDataSource(dataSource);

        return factoryBean;
    }

}
