package be.gerard.jdbc;

import be.gerard.jdbc.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.QueryMappingConfiguration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.DefaultQueryMappingConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@EnableJdbcRepositories(
        namedQueriesLocation = "jdbc-named-queries.properties"
)
@Import({
        //FileBasedNamedQueryConfiguration.class
})
public class PersonConfiguration extends AbstractJdbcConfiguration {

    /*
    @Bean
    NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .build();
    }
     */

    @Bean
    QueryMappingConfiguration rowMappers() {
        return new DefaultQueryMappingConfiguration()
                .registerRowMapper(Person.class, new PersonRowMapper());
    }

    private static class PersonRowMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(
                final ResultSet rs,
                final int i
        ) throws SQLException {
            return Person.builder()
                    .id(rs.getLong("ID"))
                    .firstName(rs.getString("FIRST_NAME"))
                    .lastName(rs.getString("LAST_NAME"))
                    .build();
        }
    }

}
