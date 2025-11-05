FROM openjdk:17-jre-slim

ENV AZURE_SQL_SERVER=""
ENV AZURE_SQL_DB=""
ENV AZURE_SQL_USER=""
ENV AZURE_SQL_PASSWORD=""

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]