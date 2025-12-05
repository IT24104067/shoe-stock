# Shoe Stock Application - Startup Instructions

## Prerequisites
1. Make sure Java 21 is installed
2. Make sure MongoDB is running on localhost:27017

## Quick Start Options

### Option 1: Using Batch File (Windows)
```bash
start.bat
```

### Option 2: Using PowerShell
```powershell
.\start.ps1
```

### Option 3: Manual Start
```bash
mvn clean package -DskipTests
java -jar target\shoe-stock-0.0.1-SNAPSHOT.jar --server.port=8081
```

## Access the Application
After starting, the application will be available at:
- **Main page**: http://localhost:8081
- **Login**: http://localhost:8081/views/login.html
- **Register**: http://localhost:8081/views/register.html
- **Shop**: http://localhost:8081/views/shoes-list.html

## Troubleshooting

### Port Conflicts
The application now runs on port 8081 (changed from 8080 to avoid conflicts). If you still get port conflicts:
```bash
java -jar target\shoe-stock-0.0.1-SNAPSHOT.jar --server.port=8082
```

### MongoDB Connection
- Ensure MongoDB is running on localhost:27017
- Check MongoDB status: `mongod --version`

### Build Issues
- Ensure Java 21 is installed: `java -version`
- Ensure Maven is installed: `mvn -version`

## Application Features
- **Frontend**: Served from the `frontend/` directory
- **API**: RESTful endpoints under `/api/`
- **Security**: JWT-based authentication
- **Database**: MongoDB for data persistence

## What Was Fixed
1. **Port Conflict**: Changed default port from 8080 to 8081
2. **Static File Serving**: Added WebConfig to properly serve frontend files
3. **Security Configuration**: Updated to allow access to static resources
4. **Startup Scripts**: Created proper startup scripts for Windows

The application now properly serves both the REST API and the frontend files without conflicts.
