FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src/ src/
RUN ./mvnw -DskipTests clean package

FROM eclipse-temurin:21-jre-jammy

RUN useradd --system --create-home --uid 10001 spring

WORKDIR /app

COPY --from=build --chown=spring:spring /workspace/target/*.jar app.jar

USER spring

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
