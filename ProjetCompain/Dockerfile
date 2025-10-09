FROM amazoncorretto:17

RUN rm -rf /var/lib/yum/yumdb/* || true

WORKDIR /app
COPY target/*.jar app.jar
# Supprime les métadonnées Maven et autres fichiers inutiles si tu les copies
RUN rm -rf /root/.m2 || true
ENTRYPOINT ["java", "-jar", "app.jar"]
