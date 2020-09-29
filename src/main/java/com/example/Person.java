package com.example;

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Introspected
public class Person {

    @NotBlank
    @Size(min=3)
    private String name;
    @NotBlank
    @Size(min=5, max=10)
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
