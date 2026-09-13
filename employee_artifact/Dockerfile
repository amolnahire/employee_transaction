FROM eclipse-temurin:17-jre

WORKDIR /app

COPY build/libs/*.jar app.jar

RUN mkdir -p /app/data && \
    chown -R 1000:1000 /app

USER 1000:1000

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
