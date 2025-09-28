# Setup & Deployment Documentation

This document provides comprehensive instructions for setting up the development environment and deploying the Autobank application.

## Table of Contents

1. [Development Setup](#development-setup)
2. [Environment Configuration](#environment-configuration)
3. [Database Setup](#database-setup)
4. [Local Development](#local-development)
5. [Testing](#testing)
6. [Production Deployment](#production-deployment)
7. [Docker Deployment](#docker-deployment)
8. [Monitoring & Maintenance](#monitoring--maintenance)
9. [Troubleshooting](#troubleshooting)

## Development Setup

### Prerequisites

#### Required Software
- **Java 17 or higher** - [Download OpenJDK](https://adoptium.net/)
- **Gradle 7.0+** - Included via Gradle Wrapper
- **Git** - [Download Git](https://git-scm.com/)
- **IDE** - IntelliJ IDEA (recommended) or VS Code

#### Optional Tools
- **Docker** - For containerized deployment
- **Azure CLI** - For Azure resource management
- **Postman** - For API testing

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/appKom/gnocchitony.git
   cd gnocchitony
   ```

2. **Verify Java Installation**
   ```bash
   java -version
   # Should show Java 17 or higher
   ```

3. **Test Gradle Build**
   ```bash
   ./gradlew build
   ```

4. **IDE Setup**
   - **IntelliJ IDEA**: Open the project folder, IDE will auto-detect Gradle
   - **VS Code**: Install Java Extension Pack and Gradle for Java extensions

## Environment Configuration

### Configuration Files

The application uses multiple configuration files:

- `src/main/resources/application.properties` - Base configuration
- `src/main/resources/application-local.properties` - Local development overrides

### Required Environment Variables

Create a `.env` file in the project root or set system environment variables:

```bash
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:sqlserver://your-server:1433;database=autobank;encrypt=true;trustServerCertificate=false
SPRING_DATASOURCE_USERNAME=your-username
SPRING_DATASOURCE_PASSWORD=your-password

# Auth0 Configuration
AUTH0_AUDIENCE=https://online.ntnu.no
SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=https://auth.online.ntnu.no/
AUTH0_DOMAIN=https://auth.online.ntnu.no

# Azure Storage Configuration
AZURE_STORAGE_CONNECTION_STRING=DefaultEndpointsProtocol=https;AccountName=your-account;AccountKey=your-key;EndpointSuffix=core.windows.net
AZURE_STORAGE_CONTAINER_NAME=autobankattachments

# Application Configuration
ENVIRONMENT=dev
ADMIN_COMMITTEE=Applikasjonskomitee
SERVER_PORT=8080
```

### Local Development Configuration

For local development, create `src/main/resources/application-local.properties`:

```properties
# Local database (if using local SQL Server)
spring.datasource.url=jdbc:sqlserver://localhost:1433;database=autobank_local;encrypt=false;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=YourLocalPassword

# Development settings
environment=dev
logging.level.com.example.autobank=DEBUG
logging.level.org.springframework.security=DEBUG

# Disable security for local testing (optional)
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
```

## Database Setup

### Azure SQL Server (Production)

1. **Create Azure SQL Server**
   ```bash
   az sql server create \
     --name autobank-sql-server \
     --resource-group autobank-rg \
     --location "West Europe" \
     --admin-user autobankadmin \
     --admin-password "YourSecurePassword123!"
   ```

2. **Create Database**
   ```bash
   az sql db create \
     --resource-group autobank-rg \
     --server autobank-sql-server \
     --name autobankdb \
     --service-objective Basic
   ```

3. **Configure Firewall**
   ```bash
   # Allow Azure services
   az sql server firewall-rule create \
     --resource-group autobank-rg \
     --server autobank-sql-server \
     --name AllowAzureServices \
     --start-ip-address 0.0.0.0 \
     --end-ip-address 0.0.0.0

   # Allow your IP (for development)
   az sql server firewall-rule create \
     --resource-group autobank-rg \
     --server autobank-sql-server \
     --name AllowMyIP \
     --start-ip-address YOUR_IP \
     --end-ip-address YOUR_IP
   ```

### Local SQL Server (Development)

#### Using Docker
```bash
# Run SQL Server in Docker
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=YourLocalPassword123!" \
   -p 1433:1433 --name sql-server-autobank \
   -d mcr.microsoft.com/mssql/server:2019-latest

# Connect and create database
docker exec -it sql-server-autobank /opt/mssql-tools/bin/sqlcmd \
   -S localhost -U sa -P "YourLocalPassword123!" \
   -Q "CREATE DATABASE autobank_local"
```

#### Using Local Installation
1. Install SQL Server Developer Edition
2. Create database `autobank_local`
3. Run the schema creation script: `schema.sql`

### Database Schema Initialization

The application will automatically create tables on startup using JPA/Hibernate. Alternatively, run the schema manually:

```sql
-- Run the contents of schema.sql file
-- This creates all required tables and constraints
```

## Local Development

### Running the Application

1. **Using Gradle**
   ```bash
   ./gradlew bootRun
   ```

2. **Using IDE**
   - Run the `AutobankApplication.kt` main class
   - Set active profile to `local` if needed

3. **With Custom Profile**
   ```bash
   ./gradlew bootRun --args='--spring.profiles.active=local'
   ```

### Development Workflow

1. **Start the Application**
   ```bash
   ./gradlew bootRun
   ```

2. **Verify Application is Running**
   ```bash
   curl http://localhost:8080/actuator/health
   ```

3. **Test API Endpoints**
   ```bash
   # Get user info (in dev mode, security is disabled)
   curl http://localhost:8080/api/auth/getuser
   
   # Get committees
   curl http://localhost:8080/api/committee/all
   ```

### Hot Reload Setup

For faster development, enable Spring Boot DevTools:

1. **Add DevTools Dependency** (already included)
   ```kotlin
   developmentOnly("org.springframework.boot:spring-boot-devtools")
   ```

2. **IDE Configuration**
   - **IntelliJ**: Enable "Build project automatically" in settings
   - **VS Code**: Save files to trigger reload

## Testing

### Running Tests

```bash
# Run all tests
./gradlew test

# Run tests with coverage
./gradlew test jacocoTestReport

# Run specific test class
./gradlew test --tests "com.example.autobank.controller.ReceiptControllerTest"
```

### Test Configuration

Create `src/test/resources/application-test.properties`:

```properties
# Use H2 in-memory database for tests
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop

# Disable security for tests
environment=dev

# Test-specific settings
logging.level.com.example.autobank=DEBUG
```

### Integration Testing

```bash
# Run integration tests
./gradlew integrationTest

# Run with test containers (if configured)
./gradlew testcontainersTest
```

## Production Deployment

### Azure App Service Deployment

1. **Create App Service**
   ```bash
   az appservice plan create \
     --name autobank-plan \
     --resource-group autobank-rg \
     --sku B1 \
     --is-linux

   az webapp create \
     --resource-group autobank-rg \
     --plan autobank-plan \
     --name autobank-app \
     --runtime "JAVA|17-java17"
   ```

2. **Configure Application Settings**
   ```bash
   az webapp config appsettings set \
     --resource-group autobank-rg \
     --name autobank-app \
     --settings \
     SPRING_DATASOURCE_URL="your-database-url" \
     AUTH0_AUDIENCE="https://online.ntnu.no" \
     ENVIRONMENT="prod"
   ```

3. **Deploy Application**
   ```bash
   # Build the application
   ./gradlew build

   # Deploy using Azure CLI
   az webapp deploy \
     --resource-group autobank-rg \
     --name autobank-app \
     --src-path build/libs/autobank-0.0.1-SNAPSHOT.jar \
     --type jar
   ```

### Manual Deployment

1. **Build Production JAR**
   ```bash
   ./gradlew build -Pprod
   ```

2. **Copy to Server**
   ```bash
   scp build/libs/autobank-0.0.1-SNAPSHOT.jar user@server:/opt/autobank/
   ```

3. **Run on Server**
   ```bash
   # Create systemd service file
   sudo nano /etc/systemd/system/autobank.service
   ```

   ```ini
   [Unit]
   Description=Autobank Application
   After=network.target

   [Service]
   Type=simple
   User=autobank
   WorkingDirectory=/opt/autobank
   ExecStart=/usr/bin/java -jar autobank-0.0.1-SNAPSHOT.jar
   Restart=always
   RestartSec=10

   Environment=SPRING_PROFILES_ACTIVE=prod
   Environment=SPRING_DATASOURCE_URL=your-database-url
   Environment=AUTH0_AUDIENCE=https://online.ntnu.no

   [Install]
   WantedBy=multi-user.target
   ```

   ```bash
   # Enable and start service
   sudo systemctl enable autobank
   sudo systemctl start autobank
   ```

## Docker Deployment

### Building Docker Image

1. **Using Provided Dockerfile**
   ```bash
   docker build -t autobank:latest .
   ```

2. **Multi-stage Build** (if Dockerfile supports it)
   ```bash
   docker build --target production -t autobank:prod .
   ```

### Running with Docker

1. **Single Container**
   ```bash
   docker run -d \
     --name autobank-app \
     -p 8080:8080 \
     -e SPRING_DATASOURCE_URL="your-database-url" \
     -e AUTH0_AUDIENCE="https://online.ntnu.no" \
     -e ENVIRONMENT="prod" \
     autobank:latest
   ```

2. **Docker Compose**
   
   Create `docker-compose.yml`:
   ```yaml
   version: '3.8'
   services:
     autobank:
       build: .
       ports:
         - "8080:8080"
       environment:
         - SPRING_PROFILES_ACTIVE=prod
         - SPRING_DATASOURCE_URL=${DATABASE_URL}
         - AUTH0_AUDIENCE=${AUTH0_AUDIENCE}
         - AZURE_STORAGE_CONNECTION_STRING=${AZURE_STORAGE_CONNECTION_STRING}
       depends_on:
         - database
       restart: unless-stopped

     database:
       image: mcr.microsoft.com/mssql/server:2019-latest
       environment:
         - ACCEPT_EULA=Y
         - SA_PASSWORD=YourSecurePassword123!
       ports:
         - "1433:1433"
       volumes:
         - sqlserver_data:/var/opt/mssql

   volumes:
     sqlserver_data:
   ```

   ```bash
   # Run with Docker Compose
   docker-compose up -d
   ```

### Container Registry

1. **Push to Azure Container Registry**
   ```bash
   # Login to ACR
   az acr login --name autobankregistry

   # Tag image
   docker tag autobank:latest autobankregistry.azurecr.io/autobank:latest

   # Push image
   docker push autobankregistry.azurecr.io/autobank:latest
   ```

## Monitoring & Maintenance

### Health Checks

The application includes Spring Boot Actuator endpoints:

```bash
# Health check
curl http://localhost:8080/actuator/health

# Application info
curl http://localhost:8080/actuator/info

# Metrics
curl http://localhost:8080/actuator/metrics
```

### Logging

1. **Log Configuration**
   ```properties
   # Production logging
   logging.level.com.example.autobank=INFO
   logging.level.org.springframework=WARN
   logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
   logging.file.name=/var/log/autobank/application.log
   ```

2. **Log Rotation**
   ```bash
   # Configure logrotate
   sudo nano /etc/logrotate.d/autobank
   ```

   ```
   /var/log/autobank/*.log {
       daily
       rotate 30
       compress
       delaycompress
       missingok
       create 644 autobank autobank
   }
   ```

### Database Maintenance

1. **Backup Strategy**
   ```bash
   # Azure SQL Database backup (automatic)
   az sql db export \
     --resource-group autobank-rg \
     --server autobank-sql-server \
     --name autobankdb \
     --storage-key-type StorageAccessKey \
     --storage-key "your-storage-key" \
     --storage-uri "https://yourstorage.blob.core.windows.net/backups/autobank-backup.bacpac"
   ```

2. **Performance Monitoring**
   ```sql
   -- Check database performance
   SELECT 
       query_stats.query_hash,
       SUM(query_stats.total_worker_time) / SUM(query_stats.execution_count) AS avg_cpu_time,
       MIN(query_stats.statement_text) AS statement_text
   FROM sys.dm_exec_query_stats AS query_stats
   CROSS APPLY sys.dm_exec_sql_text(query_stats.sql_handle)
   GROUP BY query_stats.query_hash
   ORDER BY avg_cpu_time DESC;
   ```

## Troubleshooting

### Common Issues

#### 1. Database Connection Issues

**Problem**: Cannot connect to database
```
java.sql.SQLException: The TCP/IP connection to the host has failed
```

**Solutions**:
- Check firewall rules
- Verify connection string
- Test network connectivity
- Check Azure SQL Server status

#### 2. Authentication Issues

**Problem**: JWT token validation fails
```
org.springframework.security.oauth2.jwt.JwtValidationException
```

**Solutions**:
- Verify Auth0 configuration
- Check audience and issuer settings
- Validate JWT token format
- Check system clock synchronization

#### 3. File Upload Issues

**Problem**: Cannot upload attachments
```
com.azure.storage.blob.models.BlobStorageException
```

**Solutions**:
- Verify Azure Storage connection string
- Check container permissions
- Validate file size limits
- Check network connectivity to Azure

#### 4. Memory Issues

**Problem**: OutOfMemoryError
```
java.lang.OutOfMemoryError: Java heap space
```

**Solutions**:
```bash
# Increase heap size
java -Xmx2g -jar autobank.jar

# Or set environment variable
export JAVA_OPTS="-Xmx2g -Xms512m"
```

### Debug Mode

Enable debug logging for troubleshooting:

```properties
# Enable debug logging
logging.level.com.example.autobank=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.web=DEBUG

# Enable SQL logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Performance Tuning

1. **JVM Tuning**
   ```bash
   # Production JVM settings
   java -server \
        -Xmx2g \
        -Xms1g \
        -XX:+UseG1GC \
        -XX:MaxGCPauseMillis=200 \
        -jar autobank.jar
   ```

2. **Database Connection Pool**
   ```properties
   # HikariCP settings
   spring.datasource.hikari.maximum-pool-size=20
   spring.datasource.hikari.minimum-idle=5
   spring.datasource.hikari.idle-timeout=300000
   spring.datasource.hikari.max-lifetime=1200000
   ```

### Support Contacts

- **Development Team**: Applikasjonskomitee at NTNU Online
- **Infrastructure**: Azure Support
- **Database**: SQL Server Support
- **Authentication**: Auth0 Support

### Useful Commands

```bash
# Check application status
systemctl status autobank

# View logs
journalctl -u autobank -f

# Restart application
systemctl restart autobank

# Check port usage
netstat -tlnp | grep 8080

# Monitor system resources
htop
