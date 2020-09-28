package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;

@MicronautTest
public class PersonControllerTest {

    @Inject
    EmbeddedServer server;

    @Test
    public void testAdd() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        Person person = new Person();
        person.setName("tester");
        person.setPassword("jake");
        person.setAge(33);
        person = client.toBlocking().retrieve(HttpRequest.POST("/people", person), Person.class);
        Person personCheck = client.toBlocking().retrieve(HttpRequest.GET("/people/" + person.getName() + "?pageSize=&pageNumber=&sort="), Person.class);
        Assertions.assertEquals(person.getName(), personCheck.getName());
//        Assertions.assertEquals(Integer.valueOf(1), person.getId());
//        Assertions.assertThrows(HttpClientResponseException.class,
//                () -> client.toBlocking().retrieve(HttpRequest.POST("/people", person), Person.class),
//                "person.age: must be greater than or equal to 0");
    }


    @Test
    public void testAddNotValid() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        Person person = new Person();
        person.setName(null);
        person.setPassword("micronaut");
        person.setAge(33);
        client.toBlocking().retrieve(HttpRequest.POST("/people", person), Person.class);

//        Assertions.assertThrows(HttpClientResponseException.class,
//                () -> client.toBlocking().retrieve(HttpRequest.POST("/people", person), Person.class),
//                "person.age: must be greater than or equal to 0");
    }

    @Test
    public void testFindByName() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        Person person = client.toBlocking().retrieve(HttpRequest.GET("/people/tester?pageSize=&pageNumber=&sort="), Person.class);
        Assertions.assertNotNull(person);
    }

    @Test
    public void deleteOne() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        Person person2 = client.toBlocking().retrieve(HttpRequest.GET("/people/tester?pageSize=&pageNumber=&sort="), Person.class);
        Person person = client.toBlocking().retrieve(HttpRequest.DELETE("/people/tester"), Person.class);
        Assertions.assertNotEquals(person.getName(),person2.getName());
    }

    @Test
    public void testFindAll() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people"), Person[].class);
        Assertions.assertEquals(10, persons.length);
    }

    @Test
    public void testAllByName() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        Person[] person = client.toBlocking().retrieve(HttpRequest.GET("/people/John?pageSize=&pageNumber=&sort="), Person[].class);
        Assertions.assertNotNull(person);
    }
}
