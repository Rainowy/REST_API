# REST API 

> Simple Microservices written in Java 11.

> Technologies
- Micronaut
- MongoDB
- RabbitMQ

> How to run: (only ONE FILE needed - docker-compose.yml)
- docker-compose up
> Publisher service
- http://localhost:8080/people/
 {
     "name": "tester",
     "password": "micronaut",
     "age": "16"
 }
 will save record to DB
- http://localhost:8080/people - return all records
- for searching specific records type name and parameters:
 >http://localhost:8080/people/tester?pageSize=&pageNumber=&sortOrder=

- pageSize= default(20)
- pageNumber = default(1)
- sortOrder = default ASC. Type anything to change to DESC sorting.

- All GET endpoints will return passwords replaced with ****

- DELETE request with name record will delete it:
- http://localhost:8080/people/tester
- PUT request will modify records
- http://localhost:8080/people/tester
{
     "name": "NameChanged",
     "password": "PassChanged",
     "age": "16"
 } 
 
  > Consumer service
 - http://localhost:8081/analytics

> K6 tests
-  docker run -i loadimpact/k6 run --vus 1000 --iterations 10000 - <K6_test.js

 




