package com.example;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.validation.Valid;

@Controller("/people")
@Validated
public class PersonController implements Crudable {

    private static final String id = "_id";
    private static final String name = "name";
    private static final String password = "password";
    private static final String age = "age";

    private static final String seq = "seq";

    private final MongoRepository mongoRepository;


    MongoClient mongoClient;

    public PersonController(MongoRepository mongoRepository, MongoClient mongoClient) {

        this.mongoRepository = mongoRepository;
        this.mongoClient = mongoClient;
    }

    @Override
    public Single<Person> addOne(@Body @Valid Person person) throws Exception {




        Person add = new Person();
        add.setId((long) getNextSequence("userid"));
//        System.out.println(getNextSequence("userid"));
        add.setAge(person.getAge());
        add.setPassword(person.getPassword());
        add.setName(person.getName());
//
//        System.out.println(getNextSequence("userid"));

        return Single.fromPublisher(
                mongoRepository.getCollection()
                        .insertOne(add))
                .map(success -> person);
    }

    @Override
    public Flowable<Person> findAll() {
        return Flowable.fromPublisher(mongoRepository.getCollection()
                .find())
                .map(Person::hidePassword);
    }

    @Override
    public Flowable<Person> findByName(String name, String pageSize, String pageNumber, String sortOrder) {
        return Flowable.fromPublisher(mongoRepository.getCollection()
                .find(Filters.eq(this.name, name))
                .sort(sortOrder.isEmpty() ? (Sorts.ascending(id)) : Sorts.descending(id))
                .skip(pageNumber.isEmpty() || Integer.valueOf(pageNumber) == 0 ? (0) : Integer.valueOf(pageNumber)  -1)
                .limit(pageSize.isEmpty() ? (20) : Integer.valueOf(pageSize)))
                .map(Person::hidePassword);
    }

    @Override
    public Flowable<DeleteResult> deleteOne(String name) {
        Bson filter = Filters.eq(this.name, name);
        return Flowable.fromPublisher(mongoRepository.getCollection().deleteOne(filter));
    }

    @Override
    public Flowable<UpdateResult> updateMany(String name, @Body @Valid Person person) {

     return Flowable.fromPublisher(mongoRepository.getCollection().updateMany(
                Filters.eq(this.name, name),
                Updates.combine(
                        Updates.set(this.name, person.getName()),
                        Updates.set(password, person.getPassword()),
                        Updates.set(age, person.getAge()))
        ));
    }

    public Object getNextSequence(String name) throws Exception{

        MongoCollection<Counters> humans = mongoClient.getDatabase("humans").getCollection("counters", Counters.class);


        System.out.println("BAZA " + humans);

//        System.out.println("Oto kolekcja" + mongoRepository.getCounterCollection());
//
//        MongoCollection<Counters> counterCollection = mongoRepository.getCounterCollection();
//
//        Class<Counters> documentClass = counterCollection.getDocumentClass();
//
//        System.out.println(documentClass);


//        System.out.println("NAME TO " + name);
////        System.out.println("NATIVE TO " + mongoRepository.getNative());
//
////        com.mongodb.reactivestreams.client.MongoCollection<Counters> counters = mongoRepository.getCounterCollection();
////        MongoClient mongoClient = new MongoClient("localhost", 27017);
////        System.out.println("KOLEKCJAAAA " + mongoRepository.getCounterCollection());
//        MongoCollection<Document> counters1 = mongoClient.getDatabase("humans").getCollection("people");
//
//        System.out.println("KOLOLOKKJ " + counters1);
//        System.out.println(mongoRepository.getNative());


//        System.out.println("BAZA TO " + counters1);
//        MongoCollection<Document> collection = counters1.getCollection("people");
//        System.out.println("KOLEKCJA TO " + collection);
//
//
        BasicDBObject find = new BasicDBObject();
        find.put("_id", name);
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", 1));
//        Document obj =  collection.findOneAndUpdate(find,update);
        Counters oneAndUpdate = humans.findOneAndUpdate(find, update);
//        System.out.println( "TU OBIEKT" + obj.get("seq"));
        return oneAndUpdate.getSeq();

//                obj.get("seq");






//        counters.findOneAndUpdate(f)

//        Document findRequest = new Document();
//        findRequest.append("_id", name);
//        Document updateRequest =  new Document();
//        updateRequest.append("$inc", new Document("seq",1));
//        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
//        options.upsert(true);
//        options.returnDocument(ReturnDocument.AFTER);
//        Document returnDoc = (Document) counters.findOneAndUpdate(findRequest,updateRequest,options);
//        return (int) returnDoc.get("seq");


//        find.put("_id",name);
//
//
//        BasicDBObject update = new BasicDBObject();
//        update.put("$inc", new BasicDBObject("seq",1));
////        Publisher oneAndUpdate = counters.findOneAndUpdate(find, update);
//
//        Flowable flowable = Flowable.fromPublisher(mongoRepository.getCounterCollection().findOneAndUpdate(find, update));
//
//        Counters counters1 = new Counters();
//        return
//        Flowable.fromPublisher(mongoRepository.getCounterCollection()
//                .find(Filters.eq(this.name, name))
//                 .limit(0))
//                .map(t -> counters1.getSeq(t));

//        System.out.println(flowable.toString());


//        return flowable.filter()
//
//
//        return Flowable.fromPublisher(mongoRepository.getCounterCollection()
//                .find


//return flowable;
    }

//    public long generateSequence(String seqName) {
//        Counters counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
//                new Update().inc("seq",1), options().returnNew(true).upsert(true),
//                Counters.class);
//        return !Objects.isNull(counter) ? counter.getSeq() : 1;
//    }
}



