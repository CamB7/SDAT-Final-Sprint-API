# Stage 1: build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace/app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]