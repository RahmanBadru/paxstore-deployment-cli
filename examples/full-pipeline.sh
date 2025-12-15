#!/bin/bash

# Full Pipeline Example
# This script demonstrates a complete workflow: upload, get info, and submit

set -e  # Exit on error

# Configuration
APK_FILE="myapp.apk"
APP_NAME="My Application"
PACKAGE_NAME="com.example.myapp"

echo "========================================="
echo "PAXStore Deployment Pipeline"
echo "========================================="
echo ""

# Step 1: Upload APK
echo "Step 1: Uploading APK..."
UPLOAD_OUTPUT=$(java -jar ../target/paxstore-cli.jar upload \
  --apk-file "$APK_FILE" \
  --app-name "$APP_NAME" \
  --base-type N \
  --charge-type 0 \
  --models "A920,A930" \
  --categories "WL_PS,WL_SK" \
  --short-desc "Short description" \
  --description "Full description of the application" \
  --release-notes "Automated deployment" \
  --output json)

# Extract APK ID from JSON output
APK_ID=$(echo "$UPLOAD_OUTPUT" | grep -o '"data":[0-9]*' | grep -o '[0-9]*')

if [ -z "$APK_ID" ]; then
  echo "Failed to extract APK ID"
  echo "$UPLOAD_OUTPUT"
  exit 1
fi

echo "✓ Upload successful! APK ID: $APK_ID"
echo ""

# Step 2: Get app information
echo "Step 2: Retrieving app information..."
java -jar ../target/paxstore-cli.jar get-app-info \
  --package-name "$PACKAGE_NAME" \
  --app-name "$APP_NAME"
echo ""

# Step 3: Get APK information
echo "Step 3: Retrieving APK information..."
java -jar ../target/paxstore-cli.jar get-apk-info --apk-id "$APK_ID"
echo ""

# Step 4: Submit for approval
echo "Step 4: Submitting APK for approval..."
java -jar ../target/paxstore-cli.jar submit --apk-id "$APK_ID"
echo ""

echo "========================================="
echo "✓ Pipeline completed successfully!"
echo "APK ID: $APK_ID"
echo "========================================="
