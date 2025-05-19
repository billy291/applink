# Start with a base image containing Java runtime
FROM maven:3.8.5-openjdk-17 AS builder
COPY . .
RUN mvn clean install -DskipTests
# Add Maintainer Info
LABEL maintainer="khoana1@vietcapitalbank.com"

# Add a volume pointing to /tmp
VOLUME /tmp
VOLUME /opt/logs
# Make port 8080 available to the world outside this container
EXPOSE 1991

# The application's jar file
#ARG JAR_FILE=target/digimi-universal.jar
#ADD ${JAR_FILE} app.jar

FROM openjdk:17-jdk-slim
COPY --from=builder /target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
