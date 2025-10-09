# Image Java stable et légère
FROM amazoncorretto:17-alpine
RUN rm -rf /var/cache/apk/* /tmp/* /var/tmp/* /usr/share/man/* /usr/share/doc/* || true

WORKDIR /app

# Copie le JAR compilé par Maven
COPY target/*.jar app.jar

# Commande d'exécution
ENTRYPOINT ["java", "-jar", "app.jar"]
