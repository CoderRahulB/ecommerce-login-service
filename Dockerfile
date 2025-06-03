# ----- Stage 1: Build the application -----
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# ----- Stage 2: Run the application -----
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the built jar from stage 1
COPY --from=builder /app/target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
