#!/bin/bash

# Basic Upload Example
# This script demonstrates the simplest way to upload an APK to PAXStore

# Set your credentials (or use environment variables)
export PAXSTORE_BASE_URL=https://api.whatspos.com/p-market-api
export PAXSTORE_API_KEY=your-api-key
export PAXSTORE_API_SECRET=your-api-secret

# Upload APK
java -jar ../target/paxstore-cli.jar upload \
  --apk-file myapp.apk \
  --app-name "My Application" \
  --base-type N \
  --charge-type 0 \
  --models "A920" \
  --categories "WL_PS" \
  --short-desc "A short description of my app" \
  --description "This is a full description of what my application does and why it's useful." \
  --release-notes "Version 1.0.0 - Initial release"

# Check exit code
if [ $? -eq 0 ]; then
  echo "Upload successful!"
else
  echo "Upload failed!"
  exit 1
fi
