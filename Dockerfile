#
# Build stage
#
FROM gradle:7.4.2-jdk11 AS build
COPY . .
RUN ./gradlew clean build

FROM openjdk:11-jre-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]