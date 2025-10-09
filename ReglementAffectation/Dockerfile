FROM openjdk:17-oracle
RUN rm -rf /var/mail /var/spool/mail /usr/share/zoneinfo /usr/share/terminfo || true
VOLUME /tmp
# Copie uniquement le JAR depuis le contexte externe


COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar", "app.jar"]
