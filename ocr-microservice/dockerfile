# cbm
# ARG img
# FROM $img

#EXPOSE 80 443 9000

# ARG http_proxy
# ARG https_proxy

#spring maven
FROM openjdk:15.0.2-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]