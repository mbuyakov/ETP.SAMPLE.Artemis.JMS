FROM openjdk:11-jdk-slim

ENV  QUEUE_REPLY_TO=DKN.EFSP.STATUS
ENV  GRAYLOG_ENABLED=true
ENV  GRAYLOG_HOST=efp8.sm-soft.ru
ENV  GRAYLOG_PORT=12201
ENV  GRAYLOG_LOG_LEVEL=INFO
ENV  LOG_LEVEL_ROOT=INFO
ENV  LOG_LEVEL_EFP=DEBUG

COPY target/*.jar /application.jar

RUN groupadd -g 1001 appuser && useradd -r -u 1001 -g appuser appuser
USER appuser

ENTRYPOINT ["java", "-jar", "/application.jar", "--spring.profiles.active=prod"]
