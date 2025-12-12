# --- build stage ---
FROM registry.access.redhat.com/ubi8/openjdk-21:1.18 AS build
WORKDIR /src

# Erst Gradle Wrapper + Buildfiles für besseres Layer-Caching
COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle gradle
RUN chmod +x gradlew

# Dann Source
COPY src src

# BootJar bauen
RUN ./gradlew clean bootJar -x test

# --- runtime stage ---
FROM registry.access.redhat.com/ubi8/openjdk-21-runtime:1.18
WORKDIR /app
COPY --from=build /src/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
