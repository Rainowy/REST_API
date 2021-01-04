package com.example.Dto;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Introspected
public class Person {

    private Long id;
    @NotBlank
    @Size(min=3)
    private String name;
    @NotBlank
    @Size(min=5, max=10)
    private String password;
    private int age;

    public static Person hidePassword(Person person) {
        person.setPassword(person.getPassword().replaceAll("[^0-9]", "*"));
        return person;
    }
}
