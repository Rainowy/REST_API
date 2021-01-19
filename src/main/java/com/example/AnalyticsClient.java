package com.example;

import com.example.Dto.Person;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;

@JsonSerialize
@RabbitClient("micronaut")
public interface AnalyticsClient {

    @Binding("analytics")
    void updateAnalytics(List<List<Person>> person);

//    @Binding("analytics")
//    void updateAnalytics(Object o);
}
