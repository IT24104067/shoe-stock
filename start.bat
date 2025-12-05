@echo off
echo ===== Starting Shoe Stock Application =====

REM Kill any existing Java processes
echo Stopping any existing processes on ports 8080/8081...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8080') do taskkill /f /pid %%a 2>nul
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8081') do taskkill /f /pid %%a 2>nul
taskkill /F /IM java.exe 2>nul
taskkill /F /IM javaw.exe 2>nul

REM Wait a moment
timeout /t 3 /nobreak >nul

REM Build the application if needed
if not exist "target\shoe-stock-0.0.1-SNAPSHOT.jar" (
    echo Building application...
    call mvn clean package -DskipTests -q
    if %errorlevel% neq 0 (
        echo Build failed!
        pause
        exit /b 1
    )
)

REM Create logs directory if not exists
if not exist logs mkdir logs

REM Start the application
echo Starting application on port 8081...
echo.
echo ===================================================
echo Application will be available at: http://localhost:8081
echo ===================================================
echo.
echo Press Ctrl+C to stop the application
echo.

java -jar target\shoe-stock-0.0.1-SNAPSHOT.jar --server.port=8081

echo.
echo Application stopped.
pause
