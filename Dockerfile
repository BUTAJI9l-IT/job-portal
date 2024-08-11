# syntax=docker/dockerfile:experimental

# maven build (optionally build is taken from cache in case the source code was not changed)
FROM maven:3.9.8-amazoncorretto-21 AS build
WORKDIR /workspace/app

COPY pom.xml .
COPY src src

# unpacking jar
RUN --mount=type=cache,target=/root/.m2 mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:21
VOLUME /tmp
LABEL maintainer="JobPortal"

# caching dependencies
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# run up the application
ENTRYPOINT ["java","-cp","app:app/lib/*","com.github.butaji9l.jobportal.be.JobPortalApplication"]

# It is necessary to build with build kit on account of a mount use
# DOCKER_BUILDKIT=1 docker build -t myorg/myapp .
