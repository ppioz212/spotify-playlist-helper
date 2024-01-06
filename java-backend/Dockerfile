##
## Build stage
##
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package
#
##
## Package stage
##
#FROM openjdk:11-jre-slim
#COPY --from=build /home/app/target/crowdify-app-1.0.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar","/app.jar"]

FROM gradle:5.3.0-jdk-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle.kts settings.gradle.kts $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

RUN gradle build || return 0
COPY . .
RUN gradle clean build

# actual container
FROM adoptopenjdk/openjdk11:alpine-jre
ENV ARTIFACT_NAME=spotify-helper-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

EXPOSE 8080
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}