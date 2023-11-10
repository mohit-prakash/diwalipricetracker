FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY DiwaliPriceList.xlsx /tmp/DiwaliPriceList.xlsx
COPY target/DiwaliPriceTracker-1.0.jar DiwaliPriceTracker-1.0.jar
ENTRYPOINT ["java","-jar","/DiwaliPriceTracker-1.0.jar"]
EXPOSE 8080