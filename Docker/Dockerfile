# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-22-alpine AS build
  ##3.9.6-eclipse-temurin-22-alpine

  # Set the working directory inside the container
WORKDIR /app

  # Copy the Maven project files to the container
COPY .. .

  # Build the project and create an executable JAR file
RUN mvn clean package -DskipTests

  # Stage 2: Run the application
FROM openjdk:22-jdk

  # Set the working directory inside the container
WORKDIR /app

  # Copy the JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

  # Expose the application port (adjust if necessary)
EXPOSE 8080

  # Set the entry point to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]


##Instructions:
##docker build -t 51-burguer-app .
##docker run -p 8080:8080 51-burguer-app