FROM maven:3.6-jdk-11 AS BUILD


#COPY target/demo-*.jar demo.jar
#EXPOSE 8080
#CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "demo.jar"]

COPY /src /app/src

COPY pom.xml /app/pom.xml

RUN mvn -f /app/pom.xml install

FROM openjdk:14-alpine

RUN java -version

COPY --from=BUILD /app/target/demo-*.jar /demo.jar

COPY entrypoint.sh /
RUN chmod +x /entrypoint.sh

EXPOSE 8080

ENTRYPOINT sh entrypoint.sh
