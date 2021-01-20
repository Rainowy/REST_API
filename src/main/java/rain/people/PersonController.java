package rain.people;

import rain.people.Dto.Person;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.reactivex.Flowable;
import org.bson.conversions.Bson;

import javax.validation.Valid;

@Controller("/people")
@Validated
public class PersonController implements Crudable {

    private static final String id = "_id";
    private static final String name = "name";
    private static final String password = "password";
    private static final String age = "age";

    private final MongoRepository mongoRepository;
    private final AnalyticsClient analyticsClient;

    public PersonController(MongoRepository mongoRepository, AnalyticsClient analyticsClient) {
        this.mongoRepository = mongoRepository;
        this.analyticsClient = analyticsClient;
    }

    @Override
    public Flowable<@Valid Person> addOne(@Body @Valid Person person) {

        person.setId(mongoRepository.getNextSequence("userid", true));
//        Single.fromPublisher(mongoRepository.getCollection().insertOne(person));
        return

//                findByName(person.getName(),0,0,"ASC");

                 Flowable.fromPublisher(mongoRepository.getCollection()
                .insertOne(person))
                .map(success -> person);



    }

    @Override
    public Flowable<Person> findAll() {
        return Flowable.fromPublisher(mongoRepository.getCollection()
                .find())
                .map(Person::hidePassword);
//                .publish();

    }

    @Override
    public Flowable<Person> findByName(String name, int pageSize, int pageNumber, String sortOrder) {
        return Flowable.fromPublisher(mongoRepository.getCollection()
                .find(Filters.eq(this.name, name))
                .sort(sortOrder.isEmpty() ? (Sorts.ascending(id)) : Sorts.descending(id))
                .skip(pageNumber <= 0 ? (0) : pageNumber)
                .limit(pageSize <= 0 ? (0) : pageSize))
                .map(Person::hidePassword);
    }

    @Override
    public Flowable<Person> findById(Long id) {

//        List<Person> ludzie = new ArrayList<>();
//
//        MongoCollection<Person> collection = mongoRepository.getCollection();




        return Flowable.fromPublisher(mongoRepository.getCollection()
                .find(Filters.eq(id)))
                .map(Person::hidePassword);

//                .collect(Collectors.toList());

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

    @Override
    public Flowable<UpdateResult> updateById(@Body @Valid Person person) {
        return Flowable.fromPublisher(mongoRepository.getCollection().updateOne(
                Filters.eq(person.getId()),
                Updates.combine(
                        Updates.set(name, person.getName()),
                        Updates.set(password, person.getPassword()),
                        Updates.set(age, person.getAge()))
        ));
    }
}



