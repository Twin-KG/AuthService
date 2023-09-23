FROM openjdk:17-jdk-slim as build

#Information around who maintains the image
MAINTAINER TWINKG

# Add the application's jar to the container
COPY target/content-service-0.0.1-SNAPSHOT.jar content-service.jar

EXPOSE 9012

#execute the application
ENTRYPOINT ["java","-jar","/auth-service.jar"]