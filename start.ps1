# PowerShell script to start the Shoe Stock application
Write-Host "===== Starting Shoe Stock Application =====" -ForegroundColor Green

# Kill any existing Java processes and free up ports
Write-Host "Stopping any existing processes..." -ForegroundColor Yellow
Get-Process -Name "java" -ErrorAction SilentlyContinue | Stop-Process -Force
Get-Process -Name "javaw" -ErrorAction SilentlyContinue | Stop-Process -Force

try {
    $connections = Get-NetTCPConnection -LocalPort 8080,8081 -ErrorAction SilentlyContinue
    foreach ($conn in $connections) {
        Stop-Process -Id $conn.OwningProcess -Force -ErrorAction SilentlyContinue
    }
} catch {
    # Ignore errors
}

Start-Sleep -Seconds 2

# Build the application if needed
if (-not (Test-Path "target\shoe-stock-0.0.1-SNAPSHOT.jar")) {
    Write-Host "Building application..." -ForegroundColor Yellow
    try {
        & mvn clean package -DskipTests -q
        if ($LASTEXITCODE -ne 0) {
            throw "Maven build failed"
        }
    } catch {
        Write-Host "Build failed: $($_.Exception.Message)" -ForegroundColor Red
        Read-Host "Press Enter to exit"
        exit 1
    }
}

# Create logs directory
if (-not (Test-Path "logs")) {
    New-Item -ItemType Directory -Name "logs" | Out-Null
}

# Start the application
Write-Host "Starting application on port 8081..." -ForegroundColor Green
Write-Host ""
Write-Host "===================================================" -ForegroundColor Green
Write-Host "Application will be available at: http://localhost:8081" -ForegroundColor Cyan
Write-Host "===================================================" -ForegroundColor Green
Write-Host ""
Write-Host "Press Ctrl+C to stop the application" -ForegroundColor Yellow
Write-Host ""

& java -jar "target\shoe-stock-0.0.1-SNAPSHOT.jar" --server.port=8081
