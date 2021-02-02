package pl.morecraft.dev.settler.configuration.database;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "pl.morecraft.dev.settler.dao.mapper", sqlSessionTemplateRef = "sqlSessionTemplate-settler")
@EnableJpaRepositories("pl.morecraft.dev.settler.dao.repository")
public class MainSource {

    @Bean(name = "dataSource-settler-properties")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties getDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSource-settler")
    @Primary
    public DataSource getDataSource(@Qualifier("dataSource-settler-properties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "sqlSession-settler")
    public SqlSessionFactory sqlSessionFactorySettlerBean(@Qualifier("dataSource-settler") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate-settler")
    public SqlSessionTemplate sqlSessionTemplateBean(@Qualifier("sqlSession-settler") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

}
