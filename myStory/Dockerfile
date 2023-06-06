FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/mystory-*.jar
COPY ${JAR_FILE} app.jar
ENV PROFILE dev
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar","/app.jar"]