#!/bin/bash

echo "========================================="
echo "Building PAXStore CLI Tool"
echo "========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed"
    echo "Please install Maven first: https://maven.apache.org/install.html"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed"
    echo "Please install Java 8 or higher: https://adoptium.net/"
    exit 1
fi

echo "✓ Maven found: $(mvn -version | head -n 1)"
echo "✓ Java found: $(java -version 2>&1 | head -n 1)"
echo ""

# Clean and build
echo "Building project..."
mvn clean package

# Check if build was successful
if [ $? -eq 0 ]; then
    echo ""
    echo "========================================="
    echo "✓ Build successful!"
    echo "========================================="
    echo ""
    echo "Run the CLI tool with:"
    echo "  java -jar target/paxstore-cli.jar --help"
    echo ""
    echo "Example:"
    echo "  java -jar target/paxstore-cli.jar upload --apk-file app.apk --app-name MyApp ..."
    echo ""
else
    echo ""
    echo "❌ Build failed!"
    echo "Please check the error messages above."
    exit 1
fi
