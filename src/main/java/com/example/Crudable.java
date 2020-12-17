package com.example;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface Crudable {

    @Post
    Single<Person> addOne (Person person) throws Exception;
    @Get
    Flowable<Person> findAll();
    @Get("/{name}")
    Flowable<Person> findByName(String name, String pageSize, String pageNumber, String sort);
    @Put("/{name}")
    Flowable<UpdateResult> updateMany(String name, Person person);
    @Delete("/{name}")
    Flowable<DeleteResult> deleteOne(String name);
}

