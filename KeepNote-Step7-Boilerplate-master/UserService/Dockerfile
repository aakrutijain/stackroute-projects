FROM openjdk
WORKDIR usr/src
ADD ./target/UserService-0.0.1-SNAPSHOT.jar /usr/src/UserService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/src/UserService-0.0.1-SNAPSHOT.jar"]