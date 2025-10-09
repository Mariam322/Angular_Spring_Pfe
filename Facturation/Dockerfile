FROM eclipse-temurin:17-jdk-bookworm

# Supprimer liens symboliques cassés
RUN find /var/mail /var/spool/mail /usr/share/zoneinfo /usr/share/terminfo -xtype l -delete 2>/dev/null || true

VOLUME /tmp
COPY target/*.jar  app.jar
ENTRYPOINT ["java","-jar", "app.jar"]
