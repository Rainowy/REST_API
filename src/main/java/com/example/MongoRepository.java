package com.example;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;

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

    public MongoCollection<Counters> getCounterCollection(){
        return mongoClient
                .getDatabase("humans")
                .getCollection("counters", Counters.class);
    }

    public Document getNative(){
        return (Document) mongoClient.getDatabase("humans");

    }

//    private MongoClient connectToDatabase(MongoConnection connection) {
//        return new MongoClient(new MongoClientURI("mongodb://user:password@127.0.0.1:27017/database?authMode=scram-sha1"));
//    }


}
