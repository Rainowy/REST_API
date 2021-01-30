package rain.people;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.http.annotation.Body;
import io.reactivex.Flowable;
import org.bson.conversions.Bson;
import rain.people.Dto.Counters;
import rain.people.Dto.Person;
import javax.inject.Singleton;
import javax.validation.Valid;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class MongoRepository {

    private static final String id = "_id";
    private static final String name = "name";
    private static final String password = "password";
    private static final String age = "age";
    private static final String userId = "userid";
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

    public Flowable<Person> addOne(@Body @Valid Person person) {
        person.setId(getNextSequence(userId, true));
        return Flowable.fromPublisher(getCollection()
                .insertOne(person))
                .map(success -> person);
    }

    public Flowable<Person> findAll() {
        return Flowable.fromPublisher(getCollection()
                .find())
                .map(Person::hidePassword);
    }

    public Flowable<Person> findByName(String name, Integer pageSize, Optional<Integer> pageNumber, Optional<String> sortDesc) {
        return Flowable.fromPublisher(getCollection()
                .find(Filters.eq(this.name, name))
                .sort(sortDesc.isPresent() ? (Sorts.descending(id)) : Sorts.ascending(id))
                .skip(pageNumber.isEmpty() ? (0) : pageNumber.get())
                .limit(pageSize == null ? (0) : pageSize))
                .map(Person::hidePassword);
    }

    public Flowable<Person> findById(Optional<Long> id) {
        return Flowable.fromPublisher(getCollection()
                .find(id.isPresent() ? Filters.eq(this.id, id.get()) : Filters.eq(this.id, 1)))
                .map(Person::hidePassword);
    }

    public Flowable<DeleteResult> deleteOne(String name) {
        Bson filter = Filters.eq(this.name, name);
        return Flowable.fromPublisher(getCollection().deleteOne(filter));
    }

    public Flowable<UpdateResult> updateMany(String name, @Body @Valid Person person) {
        return Flowable.fromPublisher(getCollection().updateMany(
                Filters.eq(this.name, name),
                Updates.combine(
                        Updates.set(this.name, person.getName()),
                        Updates.set(password, person.getPassword()),
                        Updates.set(age, person.getAge()))
        ));
    }

    public Flowable<UpdateResult> updateById(@Body @Valid Person person) {
        return Flowable.fromPublisher(getCollection().updateOne(
                Filters.eq(person.getId()),
                Updates.combine(
                        Updates.set(name, person.getName()),
                        Updates.set(password, person.getPassword()),
                        Updates.set(age, person.getAge()))
        ));
    }

    public Long getNextSequence(String name, boolean increment) {
        BasicDBObject find = new BasicDBObject();
        find.put("_id", name);
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", !increment ? (-1) : 1));

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

