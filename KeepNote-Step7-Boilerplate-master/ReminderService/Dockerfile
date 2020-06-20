FROM openjdk
WORKDIR usr/src
ENV MONGO_USERNAME=root
ENV MONGO_PASSWORD=root123
ENV MONGO_DATABASE=ReminderServiceDB
ENV MONGO_PORT=27017
ENV MONGO_HOST=localhost
ADD ./target/ReminderService-0.0.1-SNAPSHOT.jar /usr/src/ReminderService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/src/ReminderService-0.0.1-SNAPSHOT.jar"]