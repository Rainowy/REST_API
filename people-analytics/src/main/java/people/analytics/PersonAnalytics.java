package people.analytics;

import io.micronaut.core.annotation.Introspected;

import java.util.List;

@Introspected
public class PersonAnalytics {

    private List<List<Person>> people;
    private String timeAdded;

    public PersonAnalytics(List<List<Person>> people, String timeAdded) {
        this.people = people;
        this.timeAdded = timeAdded;
    }

    public List<List<Person>> getPeople() {
        return people;
    }

    public void setPeople(List<List<Person>> people) {
        this.people = people;
    }

    public String getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(String timeAdded) {
        this.timeAdded = timeAdded;
    }
}
