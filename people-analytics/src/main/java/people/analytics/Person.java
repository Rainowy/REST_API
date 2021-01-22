package people.analytics;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class Person {

    private Long id;
    private String name;
}
