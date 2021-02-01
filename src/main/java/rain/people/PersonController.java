package rain.people;

import rain.people.Dto.Person;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.validation.Validated;
import io.reactivex.Flowable;
import javax.validation.Valid;
import java.util.Optional;

@Controller("/people")
@Validated
public class PersonController implements Crudable {

   private final MongoRepository mongoRepository;

    public PersonController(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Flowable<Person> addOne(@Body @Valid Person person) {
        return mongoRepository.addOne(person);
    }

    @Override
    public Flowable<Person> findAll() {
        return mongoRepository.findAll();
    }

    @Override
    public Flowable<Person> findByName(String name, Integer pageSize, Optional<Integer> pageNumber, Optional<String> sortDesc) {
        return mongoRepository.findByName(name, pageSize, pageNumber, sortDesc);
    }

    @Override
    public Flowable<Person> findById(Optional<Long> id) {
        return mongoRepository.findById(id);
    }

    @Override
    public Flowable<DeleteResult> deleteOne(String name) {
        return mongoRepository.deleteOne(name);
    }

    @Override
    public Flowable<UpdateResult> updateMany(String name, @Body @Valid Person person) {
        return mongoRepository.updateMany(name, person);
    }

    @Override
    public Flowable<UpdateResult> updateById(@Body @Valid Person person) {
        return mongoRepository.updateById(person);
    }
}



