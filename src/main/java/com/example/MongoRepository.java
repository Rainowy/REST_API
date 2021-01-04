package com.example;

import com.example.Dto.Counters;
import com.example.Dto.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.Flowable;

import javax.inject.Singleton;

import static com.mongodb.client.model.Filters.eq;

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

    public MongoCollection<Counters> getCountersCollection() {
        return mongoClient
                .getDatabase("humans")
                .getCollection("counters", Counters.class);
    }

    public Long getNextSequence(String name, boolean increment) {

        BasicDBObject find = new BasicDBObject();
        find.put("_id", name);
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", !increment ? (-1): 1));

        return Flowable.fromPublisher(getCountersCollection()
                .findOneAndUpdate(find, update))
                .map(Counters::getSeq)
                .blockingFirst();
    }

    public Long findCountersMaxId() {
        return Flowable.fromPublisher(getCountersCollection()
                .find()
                .first())
                .blockingFirst()
                .getSeq();
    }

    public Long findIdFromName() {
        return Flowable.fromPublisher(getCollection()
                .find(eq("name", "tester"))
                .first())
                .blockingFirst()
                .getId();
    }
}
