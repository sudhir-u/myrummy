# Build stage
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /build

COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests -B

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /build/target/RummyTriangle-*.war app.war

EXPOSE 8085

ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.war", "--server.port=8085"]
