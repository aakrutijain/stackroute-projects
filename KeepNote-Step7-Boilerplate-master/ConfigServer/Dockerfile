FROM openjdk
WORKDIR usr/src
EXPOSE 8888
ADD ./target/ConfigServer-0.0.1-SNAPSHOT.jar /usr/src/ConfigServer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/src/ConfigServer-0.0.1-SNAPSHOT.jar"]