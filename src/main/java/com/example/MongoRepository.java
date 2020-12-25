package com.example;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.http.annotation.Body;
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

    public Long getNextSequence(String name, boolean inc) {
        int increment;
        if(inc){
            increment = 1;
        } else increment = -1;

        BasicDBObject find = new BasicDBObject();
        find.put("_id", name);
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", increment));

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

    public void setPreviousId() {

//    BasicDBObject find = new BasicDBObject();
//    find.put("_id", "userid");
//    BasicDBObject update = new BasicDBObject();
//    update.put("seq", findCountersMaxId() - 1);
        System.out.println(findCountersMaxId());
//   return Flowable.fromPublisher(getCountersCollection()
//            .updateOne(
//                    Filters.eq("_id","userid"),
//                Updates.combine(
//                        Updates.set("seq",15L)
//                )
//            ));
//    }
        BasicDBObject find = new BasicDBObject();
        find.put("_id", "userid");
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", -3));

        Flowable.fromPublisher(getCountersCollection()
                .findOneAndUpdate(find, update));



    }
}
