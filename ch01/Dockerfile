FROM openjdk:17 AS build
COPY . .
RUN ./mvnw clean package


FROM openjdk:17
COPY --from=build /target/ch01-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "app.jar"]