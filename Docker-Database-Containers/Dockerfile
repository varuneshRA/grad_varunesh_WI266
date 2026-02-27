FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# 1. Install the JDBC driver using wget
# We download it directly into the /app/lib folder
RUN mkdir -p /app/lib && \
    wget https://jdbc.postgresql.org/download/postgresql-42.7.5.jar -O /app/lib/postgres.jar

COPY App.java .

# 2. Compilation
RUN javac -cp ".:/app/lib/postgres.jar" App.java

# 3. Execution
CMD ["java", "-cp", ".:/app/lib/postgres.jar", "App"]