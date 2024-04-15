# Usar uma base com JDK
FROM openjdk:21
LABEL authors="justronis"

COPY ./target/meuapp-0.0.1-SNAPSHOT.jar /app/meuapp.jar


CMD ["java", "-jar", "/app/meuapp.jar"]


    