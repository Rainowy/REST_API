package com.example;

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;

@Introspected
public class Person {

    @NotBlank
    private String name;
    @NotBlank
    private String password;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static Person hidePassword(Person person) {
        person.setPassword(person.getPassword().replaceAll("[^0-9]", "*"));
        return person;

    }
}
