package people.analytics;

import java.time.LocalTime;
import java.util.List;

public class PersonAnalytics {

    private List<List<Person>> people;
    private LocalTime timeAdded;

    public PersonAnalytics(List<List<Person>> people, LocalTime localTime) {
        this.people = people;
        this.timeAdded = localTime;
    }

    public List<List<Person>> getPeople() {
        return people;
    }

    public void setPeople(List<List<Person>> people) {
        this.people = people;
    }

    public LocalTime getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(LocalTime timeAdded) {
        this.timeAdded = timeAdded;
    }
}
