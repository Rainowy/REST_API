package rain.people;

import rain.people.Dto.Person;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Optional;


@Requires(notEnv = Environment.TEST)
@Filter("/people/**") //wszystko na localhost:8080 aktywuje filter
public class AnalyticsFilter implements HttpServerFilter {

    private final AnalyticsClient analyticsClient;

    public AnalyticsFilter(AnalyticsClient analyticsClient) {
        this.analyticsClient = analyticsClient;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        return Flowable
                .fromPublisher(chain.proceed(request))
                .flatMap(response ->
                        Flowable.fromCallable(() -> {

                            Optional<String> body = request.getBody(String.class);

                            if (body.isPresent()) {
                                return response;

                            } else {
                                Optional<Flowable<Person>> person = (Optional<Flowable<Person>>) response.getBody();
                                person.ifPresent(personFlowable ->
                                        analyticsClient.updateAnalytics(personFlowable.toList().blockingGet()));
                                return response;
                            }
                        }));
    }
}

