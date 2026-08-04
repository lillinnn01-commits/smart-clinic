FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src src
RUN mvn -q -DskipTests package
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/smart-clinic-1.0.0.jar app.jar
ENV DB_URL=jdbc:mysql://mysql:3306/smart_clinic?allowPublicKeyRetrieval=true\&useSSL=false \
    DB_USER=clinic \
    DB_PASSWORD=clinic123 \
    JWT_SECRET=SmartClinicDockerRuntimeSecretKey2026MustBeLongEnough
RUN useradd --system --uid 1001 clinic
USER clinic
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
