package be.gerard.jdbc.repository;

import be.gerard.jdbc.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<String> findAllFirstNamesByNamedQuery();

}
