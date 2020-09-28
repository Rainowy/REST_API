package com.example;

import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;

public interface PersonInterface {

    @Post
    public Single addOne (Person person);
    @Get
    public Single<List<Person>> findAll();
    @Get("/{name}")
    public Flowable<Object> findOne(String name, String pageSize, String pageNumber, String sort);
    @Delete("/{name}")
    public Flowable deleteOne(String name);
    @Put("/{name}")
    public Flowable updateOne(String name, Person person);
}

