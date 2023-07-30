FROM maven:3.8.3-jdk-11-slim AS build
COPY /src/ /app/src
COPY /pom.xml /app
RUN  mvn -f /app/pom.xml clean package -Dmaven.test.skip

FROM adoptopenjdk/openjdk11:alpine
EXPOSE 8080
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]