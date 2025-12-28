# ---------- STAGE 1: Build ----------
FROM maven:3.9-eclipse-temurin-21 AS builder

# Configurar encoding
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8"

# Copiar el codigo fuente
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Construir el JAR dentro del contenedor
RUN mvn clean package -DskipTests

# ---------- STAGE 2: Runtime ----------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar solo el JAR generado desde el stage anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
