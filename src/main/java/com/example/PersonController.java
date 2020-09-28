package com.example;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.bson.conversions.Bson;

import javax.validation.Valid;

@Controller("/people")
@Validated
public class PersonController implements PersonInterface {

    private final MongoClient mongoClient;

    public PersonController(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public Single addOne(@Body @Valid Person person) {
        return Single.fromPublisher(
                getCollection().insertOne(person)
        ).map(success -> person);
    }

    @Override
    public Flowable<Person> findAll() {

        return Flowable.fromPublisher(getCollection()
                .find())
                .map(Person::hidePassword);
    }

    @Override
    public Flowable<Person> findByName(String name, String pageSize, String pageNumber, String sortOrder) {
        return Flowable.fromPublisher(getCollection()
                .find(Filters.eq("name", name))
                .sort(sortOrder.isEmpty() ? (Sorts.ascending("_id")) : Sorts.descending("_id"))
                .skip(pageNumber.isEmpty() ? (1) : Integer.valueOf(pageNumber))
                .limit(pageSize.isEmpty() ? (20) : Integer.valueOf(pageSize)))
                .map(Person::hidePassword);

    }

    @Override
    public Flowable<DeleteResult> deleteOne(String name) {
        Bson filter = Filters.eq("name", name);
        return Flowable.fromPublisher(
                getCollection().deleteOne(filter));
    }

    @Override
    public Flowable<UpdateResult> updateOne(String name, @Body @Valid Person person) {
        return Flowable.fromPublisher(getCollection().updateMany(
                Filters.eq("name", name),
                Updates.combine(
                        Updates.set("name", person.getName()),
                        Updates.set("password", person.getPassword()),
                        Updates.set("age", person.getAge()))
        ));
    }

    private MongoCollection<Person> getCollection() {
        return mongoClient
                .getDatabase("humans")
                .getCollection("samochody", Person.class);

    }

//    //    @Override
//    public Person apply(Person person) {
//        person.setPassword(person.getPassword().replaceAll("[^0-9]", "*"));
//        return person;
//    }
}



