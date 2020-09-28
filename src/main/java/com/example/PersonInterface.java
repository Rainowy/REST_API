package com.example;

import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface PersonInterface {

    @Post
    public Single addOne (Person person);
    @Get
    public Flowable<Person> findAll();
    @Get("/{name}")
    public Flowable<Person> findByName(String name, String pageSize, String pageNumber, String sort);
    @Put("/{name}")
    public Flowable updateOne(String name, Person person);
    @Delete("/{name}")
    public Flowable deleteOne(String name);
}

