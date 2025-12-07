###############################################################
# DOCKERFILE — Proyecto DAOS (TP EVI 2025)
#
# Opción A (ACTIVA POR DEFECTO):
#   Compila localmente con Maven -> copia el JAR
#   Más rápido / recomendado
#	Requiere ejecutar antes: mvn clean package -DskipTests
#
# Opción B (OPCIONAL):
#   Compila dentro del contenedor (si no tenés Maven)
#   Descomentar bloque inferior
###############################################################

# ============================================
# Opción A
# ============================================

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Copia el .jar generado localmente
COPY target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# ============================================
# Opción B (OPCIONAL)
# ============================================

#FROM maven:3.9.6-eclipse-temurin-21 AS build
#WORKDIR /build

#COPY pom.xml .
#COPY src ./src

#RUN mvn -B package -DskipTests

#FROM eclipse-temurin:21-jre AS runtime
#WORKDIR /app

#COPY --from=build /build/target/*.jar app.jar

#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]
