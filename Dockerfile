FROM gradle:jdk21-jammy as build
LABEL authors="mahli"

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk && \
    apt-get clean;

WORKDIR /app

COPY / /app

RUN ./gradlew bootJar

FROM gradle:jdk21-jammy as run

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/examination.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "examination.jar"]