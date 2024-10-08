FROM maven:latest AS stage1
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY src/main/docker /app
RUN mvn clean
RUN mvn package -DskipTests

FROM openjdk:20 AS final
COPY --from=stage1 /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]