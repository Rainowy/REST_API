package people.analytics;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.List;

@Data
@Introspected
public class PersonAnalytics {

    private List<List<Person>> people;
    private String timeAdded;

    public PersonAnalytics(List<List<Person>> people, String timeAdded) {
        this.people = people;
        this.timeAdded = timeAdded;
    }
}
