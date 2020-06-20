FROM openjdk
WORKDIR usr/src
EXPOSE 8761
ADD ./target/EurekaService-0.0.1-SNAPSHOT.jar /usr/src/EurekaService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/src/EurekaService-0.0.1-SNAPSHOT.jar"]