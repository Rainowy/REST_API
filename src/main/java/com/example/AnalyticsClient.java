package com.example;

import com.example.Dto.Person;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.reactivex.Flowable;

@RabbitClient("micronaut")
public interface AnalyticsClient {

    @Binding("analytics")
    void updateAnalytics(MutableHttpResponse response);

//    @Binding("analytics")
//    void updateAnalytics(Object o);
}
