FROM registry.access.redhat.com/ubi8/openjdk-21-runtime:1.18

WORKDIR /app

COPY build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
