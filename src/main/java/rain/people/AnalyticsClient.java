package rain.people;

import rain.people.Dto.Person;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

import java.util.List;

@RabbitClient("micronaut")
public interface AnalyticsClient {

    @Binding("analytics")
    void updateAnalytics(List<Person> request);
}
