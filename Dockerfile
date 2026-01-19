
FROM maven:3.9.12-eclipse-temurin-21 AS build
WORKDIR /app

# Docker återanvänder cache för beroenden (dependencies)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Kopierar källkoden och bygger projektet, mvn package kör tester (om finns) automatiskt
COPY src ./src
RUN mvn -B package

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/SocialMediaProject-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]






