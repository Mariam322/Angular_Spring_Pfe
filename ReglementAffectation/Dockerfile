FROM openjdk:17-oracle
VOLUME /tmp
# Copie uniquement le JAR depuis le contexte externe
RUN rm -rf /var/mail /var/spool/mail /usr/share/terminfo || true

COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar", "app.jar"]
