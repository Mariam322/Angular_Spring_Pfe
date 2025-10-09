FROM amazoncorretto:17



VOLUME /tmp
# Copie uniquement le JAR depuis le contexte externe
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar", "app.jar"]
