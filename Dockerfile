# Multi-stage build for Spring Boot (Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven descriptor and source code
COPY pom.xml .
COPY src ./src

# Build the executable jar without running unit tests
RUN mvn clean package -DskipTests

# Production runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy built JAR artifact from build stage
COPY --from=build /app/target/*.jar app.jar

# Render assigns port dynamically via PORT environment variable (default: 8080)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
