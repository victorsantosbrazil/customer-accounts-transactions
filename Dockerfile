FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

ENV DB_HOST=host.docker.internal

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
