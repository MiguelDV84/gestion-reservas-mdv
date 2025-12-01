FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar el JAR generado por Maven
COPY target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]