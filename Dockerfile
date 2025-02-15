FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

COPY pom.xml .  
RUN mvn dependency:go-offline

COPY src /app/src

FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

COPY --from=builder /app /app

EXPOSE 8080

ENTRYPOINT ["mvn", "spring-boot:run"]
