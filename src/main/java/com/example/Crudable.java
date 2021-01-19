package com.example;

import com.example.Dto.Person;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.function.Consumer;

public interface Crudable {

    @Post
    Single<Person> addOne (Person person) throws Exception;
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

