FROM maven:3.6-jdk-11 AS BUILD

COPY /src /app/src

COPY pom.xml /app/pom.xml

RUN mvn -f /app/pom.xml install -DskipTests

FROM openjdk:14-alpine
#add curl to work from inside container
RUN apk --no-cache add curl

RUN java -version

COPY --from=BUILD /app/target/people-*.jar /people.jar

COPY entrypoint.sh /
COPY waitForRabbit.sh /
RUN chmod +x /entrypoint.sh
RUN chmod +x /waitForRabbit.sh

EXPOSE 8080

ENTRYPOINT sh entrypoint.sh
