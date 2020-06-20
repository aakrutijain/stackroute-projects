FROM openjdk
WORKDIR usr/src
EXPOSE 8765
ADD ./target/ZuulServer-0.0.1-SNAPSHOT.jar /usr/src/ZuulServer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/src/ZuulServer-0.0.1-SNAPSHOT.jar"]