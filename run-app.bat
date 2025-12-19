@echo off
echo Starting ShoeStock Application...
echo.
echo IMPORTANT: Keep this window open while using the app!
echo The app will be available at http://localhost:8081/index.html
echo.
echo Press Ctrl+C to stop the server when done.
echo.
cd /d %~dp0
mvn -DskipTests spring-boot:run
pause
