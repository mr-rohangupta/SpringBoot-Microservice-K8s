FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} iam-auth-service.jar
ENTRYPOINT ["java","-jar","/iam-auth-service.jar"]
EXPOSE 9100