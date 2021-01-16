package com.example;

import com.example.Dto.Person;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Filter("/**") //wszystko na localhost:8080 aktywuje filter
public class AnalyticsFilter implements HttpServerFilter {

    private final AnalyticsClient analyticsClient;

    public AnalyticsFilter(AnalyticsClient analyticsClient) {
        this.analyticsClient = analyticsClient;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        System.out.println("WORKING");
//        analyticsClient.updateAnalytics(new Person());

//        Flowable<MutableHttpResponse<?>> mutableHttpResponseFlowable = Flowable.fromPublisher(chain.proceed(request));
//        System.out.println(mutableHttpResponseFlowable);
////        System.out.println(mutableHttpResponseFlowable);
//////analyticsClient.updateAnalytics(mutableHttpResponseFlowable);
////return mutableHttpResponseFlowable;
//        System.out.println(request);
//        analyticsClient.updateAnalytics((MutableHttpResponse) mutableHttpResponseFlowable);

        return Flowable
                .fromPublisher(chain.proceed(request))


                .flatMap(response ->
                        Flowable.fromCallable(() -> {
                            System.out.println(response.getBody().toString());
//                            Optional<Object> body1 = (Optional<Object>) response.getBody();
//                            Object body1 = response.body();
//                            System.out.println("BOTY TO " + body1);
//                            Optional<?> body = response.getBody;
                            Optional<Person> body = response.getBody(Person.class);
                            System.out.println(body);
//                            Optional<Person> person = response.getBody(Person.class);
//                          .ifPresent(analyticsClient::updateAnalytics);
//                            response.getBody();
//                            Optional<List<Person>> person = response.getBody(<List<Person.class>);
//                            .ifPresent(analyticsClient::updateAnalytics(response.getBody()));

                            return response;
                        })
                );


    }
}
