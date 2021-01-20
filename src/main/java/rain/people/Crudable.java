package rain.people;

import rain.people.Dto.Person;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.reactivex.Flowable;

import javax.validation.Valid;

public interface Crudable {

@Post
Flowable<@Valid Person> addOne (Person person) throws Exception;
    @Get
    Flowable<Person> findAll();
    @Get("/{name}")
    Flowable<Person> findByName(String name, int pageSize, int pageNumber, String sort);
    @Get("/id")
    Flowable<Person> findById(Long id);
    @Put("/{name}")
    Flowable<UpdateResult> updateMany(String name, Person person);
    @Put("/update")
    Flowable<UpdateResult> updateById(Person person);
    @Delete("/{name}")
    Flowable<DeleteResult> deleteOne(String name);
}

