# Build user-module module
FROM maven:3.8.4-openjdk-17-slim AS user-module-build
WORKDIR /user-module
COPY user-module/pom.xml .
COPY user-module/src ./src
RUN mvn clean install -DskipTests

# Build user-service
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY user-service/pom.xml .
COPY user-service/src ./src

# Copy user-module JAR from the previous stage
COPY --from=user-module-build /user-module/target/user-module-*.jar /root/.m2/repository/com/configmanager/user-module/1.0.0/

RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]