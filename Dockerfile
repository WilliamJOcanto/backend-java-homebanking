FROM gradle:8.8-jdk17-alpine

COPY . .

RUN gradle build

EXPOSE 8080

ENTRYPOINT [" java","-jar","build/libs/Homebanking-0.0.1-SNAPSHOT.jar"]