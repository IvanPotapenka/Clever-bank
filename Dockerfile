FROM openjdk:17-jdk-alpine
ADD /target/Clever-Bank-1.0-SNAPSHOT.jar Clever-Bank-1.0-SNAPSHOT.jar/
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/Clever-Bank-1.0-SNAPSHOT.jar"]