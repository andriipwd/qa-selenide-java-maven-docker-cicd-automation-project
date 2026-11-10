
# Java 21 and Maven
FROM maven:3.10.0-eclipse-temurin-21-alpine

# Install Chromium and its matching WebDriver
RUN apk add --no-cache chromium chromium-chromedriver \
    && addgroup -S tester \
    && adduser -S -G tester -h /home/tester tester \
    && mkdir -p /app /home/tester/.m2 \
    && chown -R tester:tester /app /home/tester

# Configure Maven's local dependency directory
ENV MAVEN_CONFIG=/home/tester/.m2

# Set project directory
WORKDIR /app

# Copy Maven configuration
COPY --chown=tester:tester pom.xml .

# Download dependencies before copying source code
USER tester
RUN mvn -B dependency:go-offline

# Copy project source code
COPY --chown=tester:tester src ./src

# Run all tests in headless mode
CMD ["mvn", "-B", "test", "-Dheadless=true", "-Dselenide.browserBinary=/usr/bin/chromium-browser", "-Dchromeoptions.args=--no-sandbox,--disable-dev-shm-usage"]