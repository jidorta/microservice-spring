FROM docker.io/eclipse-temurin:24-jre-alpine
COPY target/*.jar app.jar
CMD java -Xmx300m -Xms300m -jar app.jar
EXPOSE 8081
