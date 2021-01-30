package rain.people;

import io.micronaut.http.annotation.*;
import rain.people.Dto.Person;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.reactivex.Flowable;
import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.Optional;

public interface Crudable {

    @Post
    Flowable<@Valid Person> addOne(Person person) throws Exception;

    @Get
    Flowable<Person> findAll();

    @Get("/{name}{?pageSize,pageNumber,sortDesc}")
    Flowable<Person> findByName(String name, @Nullable Integer pageSize, Optional<Integer> pageNumber, Optional<String> sortDesc);

    @Get("/id")
    Flowable<Person> findById(Optional<Long> id);

    @Put("/{name}")
    Flowable<UpdateResult> updateMany(String name, Person person);

    @Put("/update")
    Flowable<UpdateResult> updateById(Person person);

    @Delete("/{name}")
    Flowable<DeleteResult> deleteOne(String name);
}

