FROM openjdk
WORKDIR usr/src
EXPOSE 8761
ADD ./target/EurekaServer-1.0.jar /usr/src/EurekaServer-1.0.jar
ENTRYPOINT ["java", "-jar", "/usr/src/EurekaServer-1.0.jar"]