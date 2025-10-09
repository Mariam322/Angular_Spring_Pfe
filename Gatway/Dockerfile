# Image Java stable et légère
FROM amazoncorretto:17-alpine
WORKDIR /app

# Copie le JAR compilé par Maven
COPY target/*.jar app.jar

# Commande d'exécution
ENTRYPOINT ["java", "-jar", "app.jar"]
