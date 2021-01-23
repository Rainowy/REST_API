package people.analytics;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Introspected
@AllArgsConstructor
public class Person {

    private Long id;
    private String name;
}
