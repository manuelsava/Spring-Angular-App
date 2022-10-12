FROM openjdk:17-oracle
VOLUME /main-app
#ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar

ADD target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]