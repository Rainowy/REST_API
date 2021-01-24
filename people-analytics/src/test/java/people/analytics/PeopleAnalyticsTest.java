package people.analytics;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class PeopleAnalyticsTest {

    private final String name = "first";

    @Inject
    AnalyticsService analyticsService;

    @Test
    void updatePeopleAnalytics() {

        List<Person> testPeople = new ArrayList<>();
        List<Person> testPeople2 = new ArrayList<>();
        List<Person> testPeople3 = new ArrayList<>();

        Person firstPerson = new Person(1L, "first");
        Person secondPerson = new Person(2L, "second");
        Person thirdPerson = new Person(3L, "third");
        Person fourthPerson = new Person(4L, "fourth");

        testPeople.add(firstPerson);
        testPeople.add(secondPerson);

        testPeople2.add(thirdPerson);
        testPeople2.add(fourthPerson);

        testPeople3.add(fourthPerson);
        testPeople3.add(firstPerson);

        //ConcurentHashMap and HashMap does not accept duplicates keys
        analyticsService.updatePeopleAnalytics(testPeople);
        analyticsService.updatePeopleAnalytics(testPeople);
        analyticsService.updatePeopleAnalytics(testPeople2);
        analyticsService.updatePeopleAnalytics(testPeople2);
        analyticsService.updatePeopleAnalytics(testPeople3);

        List<PersonAnalytics> personAnalytics = analyticsService.listAnalytics();

        assertEquals(findPersonInList(firstPerson, personAnalytics).getName(), name);
        assertEquals(3, personAnalytics.size());

        findPersonInList(firstPerson, personAnalytics);
    }

    private Person findPersonInList(Person p, List<PersonAnalytics> analytics) {

        Optional<List<Person>> firstEntry = getPeople(analytics);

        return firstEntry.get()
                .stream()
                .filter(person -> person.getName().equals(p.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("People not found"));
    }

    private Optional<List<Person>> getPeople(List<PersonAnalytics> analytics) {
        return analytics
                .stream()
                .map(PersonAnalytics::getPeople)
                .findFirst();
    }
}
