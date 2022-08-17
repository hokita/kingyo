FROM openjdk:11 AS builder
WORKDIR /server
ADD ./server /server
RUN ./gradlew build

FROM openjdk:11
WORKDIR /server
COPY --from=builder /server/build/libs/*.jar /server/server.jar
ENTRYPOINT ["java","-jar","/server/server.jar"]
