package com.example;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;

import javax.inject.Singleton;

@Singleton
public class MongoRepository {

    private final MongoClient mongoClient;

    public MongoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public MongoCollection<Person> getCollection() {
        return mongoClient
                .getDatabase("humans")
                .getCollection("people", Person.class);
    }
}
