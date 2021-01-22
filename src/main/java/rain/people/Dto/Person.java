package rain.people.Dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Introspected
public class Person {

    private Long id;
    @NotBlank
    @Size(min = 3)
    private String name;
    @NotBlank
    @Size(min = 5, max = 10)
    private String password;
    private int age;

    public static Person hidePassword(Person person) {
        person.setPassword(person.getPassword().replaceAll(".", "*"));
        return person;
    }
}
