FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/backend.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]

