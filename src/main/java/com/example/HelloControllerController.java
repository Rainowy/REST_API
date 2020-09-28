package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/hello")
public class HelloControllerController {

    @Get(uri="/", produces="text/plain")
    public String hello() {
        return "Hello World";
    }
}