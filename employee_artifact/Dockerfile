
FROM eclipse-temurin:17-jre

WORKDIR /app

# Download Datadog Java APM tracer
ADD https://dtdg.co/latest-java-tracer /dd-java-agent.jar

COPY build/libs/*.jar app.jar

RUN mkdir -p /app/data && \
    chown -R 1000:1000 /app && \
    chown 1000:1000 /dd-java-agent.jar

ENV JAVA_TOOL_OPTIONS="-javaagent:/dd-java-agent.jar"

USER 1000:1000

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]

