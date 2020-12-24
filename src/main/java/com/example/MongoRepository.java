package com.example;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.Flowable;

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

    public MongoCollection<Counters> getCountersCollection() {
        return mongoClient
                .getDatabase("humans")
                .getCollection("counters", Counters.class);
    }

    public Long getNextSequence(String name) {
        BasicDBObject find = new BasicDBObject();
        find.put("_id", name);
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", 1));

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
                .find(Filters.eq("name","tester"))
                .first())
                .blockingFirst()
                .getId();
    }

public void setPreviousId() {

    BasicDBObject find = new BasicDBObject();
    find.put("_id", "userid");
    BasicDBObject update = new BasicDBObject();
    update.put("seq", findCountersMaxId() - 1);

        Flowable.fromPublisher(getCountersCollection()
                .findOneAndUpdate(find,update));



    }

}
