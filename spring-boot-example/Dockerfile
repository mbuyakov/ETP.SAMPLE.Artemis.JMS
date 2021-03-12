FROM openjdk:15-jdk-slim

COPY target/*.jar /application.jar

RUN groupadd -g 1001 appuser && useradd -r -u 1001 -g appuser appuser
USER appuser

ENTRYPOINT ["java", "-jar", "/application.jar"]
