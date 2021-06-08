FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/base-framework-1.0.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${APP_PROFILE}", "app.jar"]