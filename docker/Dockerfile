# Etapa 1: Compilación
FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
WORKDIR /proyect
COPY /src/demo/pom.xml /proyect
RUN mvn dependency:go-offline
COPY /src/demo/src /proyect/src
RUN mvn -B package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre
WORKDIR /usr/src/app/
COPY --from=builder /proyect/target/*.jar /usr/src/app/app.jar
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "app.jar"]
