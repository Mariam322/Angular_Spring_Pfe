FROM openjdk:17-oracle
VOLUME /tmp
# Copie uniquement le JAR depuis le contexte externe
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar", "app.jar"]
