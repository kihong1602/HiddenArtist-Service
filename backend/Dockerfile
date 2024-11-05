# Stage 1: Build Application
FROM eclipse-temurin:17-jdk AS build

# Set working Directory
WORKDIR /app

# Copy gradle wrapper and source code
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

# Make gradlew executable and build application
RUN chmod +x ./gradlew
RUN ./gradlew clean build --info -x test

# Stage 2: Create the final Image
FROM eclipse-temurin:17-jdk-alpine

# Copy Jar file from the builder stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose Application Port
EXPOSE 8080

# Run Application
ENTRYPOINT ["java","-jar","/app.jar"]