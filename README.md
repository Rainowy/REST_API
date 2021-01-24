# REST_API

> Simple Microservice written in Java 11.

> Technologies
>
- Micronaut
- MongoDB
- RabbitMQ

> SIMPLE CRUD (use Postman)
- http://localhost:8080/people/
 {
     "name": "tester",
     "password": "micronaut",
     "age": "16"
 }
 will save record to DB
- http://localhost:8080/people/ without name will return all records
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

> I Made testing with K6 framework from docker. Thats, why URL in K6_GET_test.js and K6_test.js in those files is changed to 172.17.0.1 (docker internal ip)
I was running K6 from terminal with this command:
-  docker run -i loadimpact/k6 run --vus 1000 --iterations 10000 - <K6_GET_test.js

 




