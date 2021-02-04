package be.gerard.jdbc.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder
public class Person {
    @Id
    Long id;
    String firstName;
    String lastName;
}
