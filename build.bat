@echo off
echo =========================================
echo Building PAXStore CLI Tool
echo =========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: Maven is not installed
    echo Please install Maven first: https://maven.apache.org/install.html
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: Java is not installed
    echo Please install Java 8 or higher: https://adoptium.net/
    exit /b 1
)

echo Maven found
echo Java found
echo.

REM Clean and build
echo Building project...
mvn clean package

REM Check if build was successful
if %ERRORLEVEL% EQU 0 (
    echo.
    echo =========================================
    echo Build successful!
    echo =========================================
    echo.
    echo Run the CLI tool with:
    echo   java -jar target\paxstore-cli.jar --help
    echo.
    echo Example:
    echo   java -jar target\paxstore-cli.jar upload --apk-file app.apk --app-name MyApp ...
    echo.
) else (
    echo.
    echo Build failed!
    echo Please check the error messages above.
    exit /b 1
)
