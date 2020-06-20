From java:8-jre
WORKDIR usr/lib
ENV MONGO_DATABASE=gipherdb
ENV MONGO_URL=mongodb://localhost:27017/gipherdb
ADD ./target/gipherrecommendersystem-1.0.jar /usr/lib/gipherrecommendersystem-1.0.jar
ENTRYPOINT ["java","-jar","gipherrecommendersystem-1.0.jar"]