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
import io.reactivex.functions.Function;
import org.bson.conversions.Bson;

import javax.validation.Valid;
import java.util.List;

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
    public Single<List<Person>> findAll() {

//        List<Person> products = new ArrayList<Person>();
//        FindPublisher<Person> cur = getCollection().find();
//        while(cur.hasNext()) {
//            products.add(cur.next());
//        }

        return Flowable.fromPublisher(
                getCollection()
                        .find())
                .toList();


//                .map(person -> person.getPassword().replaceAll("[abc]","*"));




    }


    @Override
    public Flowable<Object> findOne(String name, String pageSize, String pageNumber, String sort) {
//        return Flowable.fromPublisher(
//                getCollection()
////               .find().sort({ "s": -1, "_id": 1 }).skip(<page-1>).limit(<pageSize>)
//                        .find(Filters.eq("name", name))
//                        .sort(Sorts.ascending("name"))
//                        .limit(3)
//
//        ).toList();


        Flowable<Object> personFlowable =  Flowable.fromPublisher(getCollection()
                .find(Filters.eq("name", name))
                .sort(sort.isEmpty() ? (Sorts.ascending("_id")) : Sorts.descending("_id"))
                .skip(pageNumber.isEmpty() ? (0) : Integer.valueOf(pageNumber))
                .limit(pageSize.isEmpty() ? (0) : Integer.valueOf(pageSize)))
//                .map( person -> person.getPassword().replaceAll("[^0-9]","*"),
//
//                        );
                .map(new Function<Person, Person>() {
                         @Override
                         public Person apply(Person person) throws Exception {
                             person.setPassword(person.getPassword().replaceAll("[^0-9]","*"));
                             return person;
                         }
                     });
//                    @Override
//                    public User apply(User user) throws Exception {
//                        // modifying user object by adding email address
//                        // turning user name to uppercase
////                        user.setEmail(String.format("%s@rxjava.wtf", user.getName()));
////                        user.setName(user.getName().toUpperCase());
//                        return user;
//                    }
//                });
//        .map(person ->
//                person.setName(person.getName()));



//                .map(
//                    @Override
//                    public Person apply(Person person) {
//                        // modifying user object by adding email address
//                        // turning user name to uppercase
//                        person.setName(person.getName());
//                        person.setPassword(person.getPassword());
//                        return person;
//                    }
//                });

//        System.out.println(personFlowable.toList().map(person -> person.getPassword().replaceAll("[.]","*"))
//
//
//        ArrayList<Person> people = new ArrayList<>();

//        personFlowable.fromArray()

//        return personFlowable;

//
//        System.out.println(personFlowable);
        return personFlowable;

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
}



