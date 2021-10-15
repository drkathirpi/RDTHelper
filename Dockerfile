FROM openjdk:8-jdk-alpine

RUN mvn clean compile install -DskipTests --file pom.xml

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]