FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/DiwaliPriceTracker-1.0.jar DiwaliPriceTracker-1.0.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080