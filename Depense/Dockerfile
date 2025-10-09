FROM openjdk:17-oracle
RUN find /var/mail /var/spool/mail /usr/share/zoneinfo /usr/share/terminfo -xtype l -delete 2>/dev/null || true


VOLUME /tmp
COPY target/*.jar  app.jar
ENTRYPOINT ["java","-jar", "app.jar"]
