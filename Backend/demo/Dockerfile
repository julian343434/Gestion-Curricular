# Etapa 1: Compilación
FROM maven:3.8.8-eclipse-temurin-17 AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos del proyecto al contenedor
COPY pom.xml .
COPY src ./src

# Ejecutar Maven para compilar el proyecto y empaquetar el archivo JAR
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM openjdk:17-jdk-slim

# Crear un directorio para la aplicación
WORKDIR /app

# Copiar el archivo JAR desde la etapa de compilación
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
