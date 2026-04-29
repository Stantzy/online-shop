# Stage 1 - build
FROM maven:4.0.0-rc-5-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2 - run
FROM eclipse-temurin:21-jre-ubi10-minimal
WORKDIR /usr/src/app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
