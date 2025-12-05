@echo off
REM start-app.bat - starts shoe-stock jar detached and logs output
if not exist "%~dp0logs" mkdir "%~dp0logs"
REM Use start to run in new window; redirect output to files in logs folder
start "shoe-stock" cmd /c "cd /d "%~dp0" && java -jar "%~dp0target\shoe-stock-0.0.1-SNAPSHOT.jar" --server.port=8080 > "%~dp0logs\app.out" 2> "%~dp0logs\app.err""
echo started
exit /b 0
