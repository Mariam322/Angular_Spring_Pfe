FROM amazoncorretto:17-alpine
# 🧹 Nettoyage des fichiers cassés dans yumdb pour éviter les erreurs Kaniko
RUN rm -rf /var/lib/yum/yumdb/* || true

WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
