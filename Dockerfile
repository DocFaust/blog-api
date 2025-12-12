FROM registry.access.redhat.com/ubi8/openjdk-21:1.18 AS build
WORKDIR /src

ENV HOME=/tmp
ENV GRADLE_USER_HOME=/tmp/gradle

COPY --chmod=755 gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle gradle
COPY src src

RUN ./gradlew --no-daemon -g /tmp/gradle clean bootJar -x test

# --- runtime stage ---
FROM registry.access.redhat.com/ubi8/openjdk-21-runtime:1.18
WORKDIR /app
COPY --from=build /src/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
