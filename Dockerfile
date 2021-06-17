FROM maven:3.8-jdk-8 as build

COPY ./pom.xml /n26-challenge/pom.xml
WORKDIR /n26-challenge/
RUN mvn dependency:go-offline

COPY ./src/ /n26-challenge/
RUN mvn clean install

CMD mvn spring-boot:run


FROM gcr.io/distroless/java:8 as deploy
COPY --from=build /n26-challenge/target/**.jar /n26-challenge/app.jar
WORKDIR /n26-challenge
CMD ["app.jar"] 