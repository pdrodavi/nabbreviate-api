FROM adoptopenjdk/openjdk11:x86_64-ubuntu-jre-11.0.25_9
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/nabbreviate-api.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "nabbreviate-api.jar"]