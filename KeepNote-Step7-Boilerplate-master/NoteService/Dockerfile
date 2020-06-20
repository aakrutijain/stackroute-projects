FROM openjdk
WORKDIR usr/src
EXPOSE 9300
ADD ./target/NoteService-0.0.1-SNAPSHOT.jar /usr/src/NoteService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","NoteService-0.0.1-SNAPSHOT.jar"]
