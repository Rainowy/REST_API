package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import io.reactivex.Flowable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import javax.validation.constraints.AssertFalse;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class PersonControllerTest {

    @Inject
    @Client("/")
    RxHttpClient client;
    @Inject
    MongoRepository mongoRepository;

    @Test
    public void testAdd() {

        Person[] people = findAllPeople();

        Person person = new Person();
        person.setName("tester");
        person.setPassword("jackson");
        person.setAge(33);
        person = client.toBlocking().retrieve(HttpRequest.POST("/people", person), Person.class);

        // Test to check if new person id already exist in DB
        boolean exist = false;
        for (int i = 0; i <people.length ; i++) {
            if(person.getId() == people[i].getId()){
                exist = true;
            }
        }
        Assertions.assertFalse(exist);
        Assertions.assertNotNull(person);
    }

    @Test
    public void deleteOne() {
        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people/tester?pageSize=&pageNumber=&sortOrder="), Person[].class);
        Person person = client.toBlocking().retrieve(HttpRequest.DELETE("/people/tester"), Person.class);
        Assertions.assertNotEquals(person.getName(),persons[0].getName());
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
    public void findById(){
        Person person = client.toBlocking().retrieve(HttpRequest.GET("/people/id?id=20"), Person.class);
        assertEquals("IdTester",person.getName());
    }


//
//    @Test
//    public void testFindAll() {
//        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people"), Person[].class);
//        assertEquals(34, findAllPeople().length -1);
////        return persons;
//    }

    @Test
    public void testAllByName() {
        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people/tester?pageSize=&pageNumber=&sortOrder="), Person[].class);
        Assertions.assertNotNull(persons[0]);
    }

    @Test
    public void findMaxId(){
        Flowable<Counters> countersFlowable = Flowable.fromPublisher(mongoRepository.getCountersCollection()
                .find().first());

        Counters counters = countersFlowable.blockingFirst();

        System.out.println(counters.getSeq());


        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people"), Person[].class);
        Long maxId = persons[persons.length - 1].getId();
        assertNotEquals(counters.getSeq(),maxId);
    }

    public Person blankName(){
        Person person = new Person();
        person.setName(null);
        person.setPassword("micronaut");
        person.setAge(10);
        return person;
    }

    public Person[] findAllPeople(){
        return client.toBlocking().retrieve(HttpRequest.GET("/people"), Person[].class);
    }
}
