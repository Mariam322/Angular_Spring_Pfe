FROM openjdk:17-jdk-slim

RUN rm -rf /var/mail /var/spool/mail /usr/share/zoneinfo /usr/share/terminfo || true
VOLUME /tmp
COPY target/*.jar  app.jar
ENTRYPOINT ["java","-jar", "app.jar"]
