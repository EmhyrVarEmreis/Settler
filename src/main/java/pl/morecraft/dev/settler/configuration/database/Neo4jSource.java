package pl.morecraft.dev.settler.configuration.database;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("pl.morecraft.dev.settler.domain.graph")
@EnableNeo4jRepositories("pl.morecraft.dev.settler.dao.graph")
public class Neo4jSource {

}
