FROM maven AS builder

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -X

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /api

# Copy the JAR file (Spring Boot application) into the container
COPY --from=builder /build/target/demo-0.0.1-SNAPSHOT.jar /api

# Expose the port your Spring Boot application is running on (default is 8080)
EXPOSE 9090

# Command to run the Spring Boot application
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
