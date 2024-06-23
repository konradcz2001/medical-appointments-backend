# Use the official Maven image to build the application
# https://hub.docker.com/_/maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Use the official OpenJDK image to run the application
# https://hub.docker.com/_/openjdk
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/medical-appointments-backend-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/keystore.p12 /app/keystore.p12

# Expose the port the application runs on
EXPOSE 443

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
