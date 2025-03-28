# Usar una imagen base de OpenJDK
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "app.jar" ]