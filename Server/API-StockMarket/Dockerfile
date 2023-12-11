FROM openjdk:21
ADD ./target/API-StockMarket-0.0.1-SNAPSHOT.jar server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "server.jar"]