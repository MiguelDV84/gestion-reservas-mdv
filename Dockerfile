# ---------- STAGE 1: Build ----------
FROM maven:3.9-eclipse-temurin-17 AS builder

# Copiar el código fuente
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Construir el JAR dentro del contenedor
RUN mvn -e -B -DskipTests package

# ---------- STAGE 2: Runtime ----------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar solo el JAR generado desde el stage anterior
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
