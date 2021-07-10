FROM maven:3.8-jdk-8 as build

COPY ./pom.xml /n26-transactions/pom.xml
WORKDIR /n26-transactions/
RUN mvn dependency:go-offline

COPY ./src/ /n26-transactions/src/
RUN mvn clean install

CMD mvn spring-boot:run


FROM gcr.io/distroless/java:8 as deploy
COPY --from=build /n26-transactions/target/**.jar /n26-transactions/app.jar
WORKDIR /n26-transactions
CMD ["app.jar"]