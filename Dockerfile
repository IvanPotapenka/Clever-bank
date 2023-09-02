FROM openjdk:17-jdk-alpine
ADD build/libs/clever-bank-1.0-SNAPSHOT.jar Clever-Bank-1.0-SNAPSHOT.jar/
EXPOSE 8080
ENTRYPOINT ["java","-jar","build/libs/clever-bank-1.0-SNAPSHOT.jar"]