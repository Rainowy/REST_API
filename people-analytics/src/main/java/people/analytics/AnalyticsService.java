package people.analytics;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class AnalyticsService {

    private final Map<List<List<Person>>, LocalTime> personAnalytics = new ConcurrentHashMap<>();

    public void updatePeopleAnalytics(List<List<Person>> person) {

        personAnalytics.compute(person, (p, v) -> {
            v = LocalTime.now();
            if (v == null) {
                return LocalTime.now();
            } else {
                return v;
            }
        });

    }

    public List<PersonAnalytics> show() {
        return personAnalytics.entrySet().stream()
                .map(p -> new PersonAnalytics(p.getKey(), p.getValue()))
                .collect(Collectors.toList());
    }
}
