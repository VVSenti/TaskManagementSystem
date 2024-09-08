FROM eclipse-temurin:17-jdk-ubi9-minimal AS build

COPY .mvn .mvn
COPY src src
COPY mvnw .
COPY pom.xml .

RUN ./mvnw -B package -DskipTests=true


FROM eclipse-temurin:17-jre-alpine
COPY --from=build target/TaskManagementSystem-1.0.0.jar .

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "TaskManagementSystem-1.0.0.jar" ]