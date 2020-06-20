From java:8-jre
WORKDIR usr/lib
ENV MONGO_DATABASE=gipherdb
ENV MONGO_URL=mongodb://localhost:27017/gipherdb
ADD ./target/giphermanager-1.0.jar /usr/lib/giphermanager-1.0.jar
ENTRYPOINT ["java","-jar","giphermanager-1.0.jar"]