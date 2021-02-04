package be.gerard.jdbc;

import be.gerard.jdbc.model.Person;
import be.gerard.jdbc.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(SpringExtension.class)
@Transactional
@ContextConfiguration(classes = {
        PersonConfiguration.class
})
@DataJpaTest
public class FileBasedNamedQueryConfigurationTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void test() {
        personRepository.save(Person.builder()
                .firstName("Bart")
                .lastName("Gerard")
                .build()
        );

        final Iterable<Person> all = personRepository.findAll();
        System.out.println(all);

        final List<String> allFirstNames = personRepository.findAllFirstNamesByNamedQuery();
        System.out.println(allFirstNames);
    }

}
