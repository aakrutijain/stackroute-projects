FROM openjdk
WORKDIR usr/src
EXPOSE 8765
ADD ./target/ZuulServer-1.0.jar /usr/src/ZuulServer-1.0.jar
ENTRYPOINT ["java", "-jar", "/usr/src/ZuulServer-1.0.jar"]