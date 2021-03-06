package people.analytics;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;

import java.util.List;

@Requires(notEnv = Environment.TEST)
@RabbitListener
public class AnalyticsListener {

    private AnalyticsService analyticsService;

    public AnalyticsListener(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @Queue("analytics")
    public void updateAnalytics(List<Person> person) {
        analyticsService.updatePeopleAnalytics(person);
    }
}

