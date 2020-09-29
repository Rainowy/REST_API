package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class PersonControllerTest {

    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    public void testAdd() {
        Person person = new Person();
        person.setName("tester");
        person.setPassword("jackson");
        person.setAge(33);
        person = client.toBlocking().retrieve(HttpRequest.POST("/people", person), Person.class);
        Assertions.assertNotNull(person);
    }

    @Test
    public void testAddNotValid() {
        Exception exception = assertThrows(HttpClientResponseException.class, () -> client.toBlocking().retrieve(HttpRequest.POST("/people", blankName()), Person.class));
        assertEquals("person.name: must not be blank", exception.getMessage());
    }

    @Test
    public void testFindByName() {
        Person[] person = client.toBlocking().retrieve(HttpRequest.GET("/people/tester?pageSize=&pageNumber=&sortOrder="), Person[].class);
        Assertions.assertNotNull(person);
    }

    @Test
    public void deleteOne() {
        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people/tester?pageSize=&pageNumber=&sortOrder="), Person[].class);
        Person person = client.toBlocking().retrieve(HttpRequest.DELETE("/people/tester"), Person.class);
        Assertions.assertNotEquals(person.getName(),persons[0].getName());
    }

    @Test
    public void testFindAll() {
        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people"), Person[].class);
        assertEquals(15, persons.length);
    }

    @Test
    public void testAllByName() {
        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people/tester?pageSize=&pageNumber=&sortOrder="), Person[].class);
        Assertions.assertNotNull(persons[0]);
    }

    public Person blankName(){
        Person person = new Person();
        person.setName(null);
        person.setPassword("micronaut");
        person.setAge(10);
        return person;
    }
}
