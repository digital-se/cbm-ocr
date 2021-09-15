FROM maven:3.8.1-openjdk-15-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -B -f pom.xml clean package -DskipTests -Dhttps.protocols=TLSv1.2

FROM openjdk:15-jdk-slim
RUN apt-get update
RUN apt-get install tesseract-ocr -y
RUN mkdir -p /usr/share/tessdata
ADD https://raw.githubusercontent.com/tesseract-ocr/tessdata/main/por.traineddata /usr/share/tessdata/por.traineddata
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8083
ENV LC_ALL C
ENTRYPOINT ["java","-jar","app.jar"]
