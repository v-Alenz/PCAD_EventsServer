FROM eclipse-temurin:17-jdk-alpine
COPY ./target/events-api-rest-0.0.1-SNAPSHOT.jar /event_api_rest.jar
EXPOSE 8080
CMD [ "java", "-jar", "/event_api_rest.jar" ]