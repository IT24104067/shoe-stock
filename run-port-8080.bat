@echo off
echo ======================================
echo Starting Shoe Stock Application
echo Port: 8080
echo ======================================

REM Kill any existing Java processes
taskkill /F /IM java.exe 2>nul

REM Wait a moment
timeout /t 3 /nobreak >nul

REM Start the application
echo Starting application...
cd /d "%~dp0"
java -jar target\shoe-stock-0.0.1-SNAPSHOT.jar --server.port=8080

echo ======================================
echo Application startup complete
echo ======================================
pause
