FROM openjdk
WORKDIR usr/src
EXPOSE 9400
ADD ./target/CategoryService-0.0.1-SNAPSHOT.jar /usr/src/CategoryService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/src/CategoryService-0.0.1-SNAPSHOT.jar"]