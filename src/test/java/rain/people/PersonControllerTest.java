package rain.people;

import rain.people.Dto.Person;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

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
        Long counterBeforePost = mongoRepository.findCountersMaxId();
        Person person = new Person();
        person.setName("tester");
        person.setPassword("jackson");
        person.setAge(33);
        person = client.toBlocking().retrieve(HttpRequest.POST("/people", person), Person.class);
        Long counterAfterPost = mongoRepository.findCountersMaxId();
        Assertions.assertEquals(counterAfterPost, counterBeforePost +1);
        Assertions.assertNotNull(person);
    }

    @Test
    public void findById() {
        Long id = mongoRepository.findIdFromName();
        Person person = client.toBlocking().retrieve(HttpRequest.GET("/people/id?id=" + id), Person.class);
        assertEquals("tester", person.getName());
    }

    @Test
    public void testFindByName() {
        Person person = client.toBlocking().retrieve(HttpRequest.GET("/people/tester?pageSize=0&pageNumber=0&sortOrder="), Person.class);
        Assertions.assertNotNull(person);
    }

    @Test
    public void testAllByName() {
        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people/tester?pageSize=0&pageNumber=0&sortOrder="), Person[].class);
        Assertions.assertNotNull(persons);
    }

    @Test
    public void deleteOne() {
        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people/tester?pageSize=0&pageNumber=0&sortOrder="), Person[].class);
        Person person = client.toBlocking().retrieve(HttpRequest.DELETE("/people/tester"), Person.class);
        Assertions.assertNotEquals(person.getName(), persons[0].getName());
        mongoRepository.getNextSequence("userid", false);
    }

    @Test
    public void testAddNotValid() {
        Exception exception = assertThrows(HttpClientResponseException.class, () -> client.toBlocking().retrieve(HttpRequest.POST("/people", blankName()), Person.class));
        assertEquals("person.name: must not be blank", exception.getMessage());
    }

    @Test
    public void testFindAll() {
        Person[] persons = client.toBlocking().retrieve(HttpRequest.GET("/people"), Person[].class);
        assertEquals(mongoRepository.findCountersMaxId() -1, findAllPeople().length);
    }

    @Test
    public void testEndpoint(){
        String endpoint = client.toBlocking().retrieve(HttpRequest.GET("/people/tester"), String.class);
        Assertions.assertNotNull(endpoint);
    }

    @Test
    public void checkIfWrongEndpointThrowsException(){
        Exception exception = assertThrows(HttpClientResponseException.class, () -> client.toBlocking().retrieve(HttpRequest.GET("/people/id/sd"), Person.class));
        assertEquals("Page Not Found", exception.getMessage());
    }

    public Person blankName() {
        Person person = new Person();
        person.setName(null);
        person.setPassword("micronaut");
        person.setAge(10);
        return person;
    }

    public Person[] findAllPeople() {
        return client.toBlocking().retrieve(HttpRequest.GET("/people"), Person[].class);
    }
}