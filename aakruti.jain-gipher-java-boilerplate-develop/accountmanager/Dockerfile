From java:8-jre
WORKDIR usr/lib
ENV MYSQL_DATABASE=UserDB
ENV MYSQL_URL=jdbc:mysql://localhost:3306/UserDB?createDatabaseIfNotExist=true
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ADD ./target/accountmanager-1.0.jar /usr/lib/accountmanager-1.0.jar
ENTRYPOINT ["java","-jar","accountmanager-1.0.jar"]