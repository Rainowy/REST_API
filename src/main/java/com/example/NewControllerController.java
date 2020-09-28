package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/newController")
public class NewControllerController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}