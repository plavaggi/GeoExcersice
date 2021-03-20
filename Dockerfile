# build stage build the jar with all our resources
FROM maven:3.6.3-jdk-11 as build
WORKDIR /app
ADD pom.xml .
RUN mvn dependency:go-offline
ADD . .
RUN mvn clean package -DskipTests

FROM adoptopenjdk:11-jre-hotspot as runtime
WORKDIR /app
COPY --from=build /app/target/application.jar ./
ENTRYPOINT ["java", "-jar", "application.jar"]
