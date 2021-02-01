package people.analytics;

import javax.inject.Singleton;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class AnalyticsService {

    private final Map<List<Person>, String> personAnalytics = new ConcurrentHashMap<>();

    public void updatePeopleAnalytics(List<Person> person) {

        LocalDate anotherSummerDay = LocalDate.now();
        LocalTime anotherTime = LocalTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(anotherSummerDay, anotherTime, ZoneId.of("Europe/Helsinki"));
        String format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(zonedDateTime);

        personAnalytics.compute(person, (p, v) -> {
            if (v == null) {
                return format;
            } else {
                return v;
            }
        });

    }

    public List<PersonAnalytics> listAnalytics() {
        return personAnalytics
                .entrySet()
                .stream()
                .map(p -> new PersonAnalytics(p.getKey(), p.getValue()))
                .collect(Collectors.toList());
    }
}
