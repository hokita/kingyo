FROM arm64v8/gradle:jdk11 as builder
WORKDIR /server
ADD --chown=gradle:gradle ./server /server
RUN gradle clean build --no-daemon

FROM arm64v8/openjdk:11-jdk-slim-bullseye
WORKDIR /server
COPY --from=builder /server/build/libs/*.jar /server/server.jar
ENTRYPOINT ["java","-jar","/server/server.jar"]
