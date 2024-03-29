FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/DiwaliPriceList.xlsx DiwaliPriceList.xlsx
COPY target/DiwaliPriceTracker-1.0.jar DiwaliPriceTracker-1.0.jar
ENTRYPOINT ["java","-jar","/DiwaliPriceTracker-1.0.jar"]
EXPOSE 8080