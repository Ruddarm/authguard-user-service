FROM maven:3.9.4-eclipse-temurin-21-alpine

WORKDIR /userservice
COPY .mvn/  .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
ENV  SPRING_PROFILES_ACTIVE=docker
COPY src  ./src

CMD ["./mvnw", "spring-boot:run"]

