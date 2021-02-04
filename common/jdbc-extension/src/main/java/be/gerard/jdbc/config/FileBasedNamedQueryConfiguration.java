package be.gerard.jdbc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactory;
import org.springframework.data.repository.core.support.PropertiesBasedNamedQueries;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class FileBasedNamedQueryConfiguration {

    @Autowired
    JdbcRepositoryFactory factory;

    @PostConstruct
    public void factory() throws IOException {
        final PropertiesFactoryBean properties = new PropertiesFactoryBean();
        properties.setLocation(new ClassPathResource("jdbc-named-file-based-queries.properties"));
        properties.afterPropertiesSet();
        final PropertiesBasedNamedQueries namedQueries = new PropertiesBasedNamedQueries(properties.getObject());
        // TODO
        factory.setNamedQueries(namedQueries);
    }

}
