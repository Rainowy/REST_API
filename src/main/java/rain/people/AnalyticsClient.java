package rain.people;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import rain.people.Dto.Person;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

import java.util.List;

@RabbitClient("micronaut")
public interface AnalyticsClient {

    @Binding("analytics")
    void updateAnalytics(List<List<Person>> request);
}
