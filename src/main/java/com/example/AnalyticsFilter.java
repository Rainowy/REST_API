package com.example;

import com.example.Dto.Person;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import org.reactivestreams.Publisher;

import javax.imageio.plugins.bmp.BMPImageWriteParam;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
@JsonSerialize
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
//        Publisher<MutableHttpResponse<?>> proceed = chain.proceed(request);
//        System.out.println(proceed);

//Flowable<Person>

       return Flowable
                .fromPublisher(chain.proceed(request))
                .flatMap(response ->

                        Flowable.fromCallable(() -> {
                            System.out.println(response.body());
                            Object body = response.body();

                            Optional<Flowable<List<Person>>> person = (Optional<Flowable<List<Person>>>) response.getBody();
                            person.ifPresent(personFlowable ->
//                                            System.out.println(personFlowable.toList().blockingGet()));
                                    analyticsClient.updateAnalytics(personFlowable.toList().blockingGet()));
//                                   personFlowable.blockingFirst()(analyticsClient::updateAnalytics)));
//                            Optional<?> body = response.getBody();
//                            System.out.println(body);
//                            return person
//                            person.ifPresent(analyticsClient::updateAnalytics);

                            return response;
                        }));


                        }

    }

