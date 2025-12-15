# PAXStore CLI - Deployment Tool for CI/CD

A production-ready command-line interface for the PAXStore Developer SDK, designed to simplify APK deployment in CI/CD pipelines (especially Bitbucket Pipelines).

## Features

- ✅ **Upload APK** - Upload and create/update apps in one command
- ✅ **Submit for Approval** - Submit APKs for PAXStore approval
- ✅ **Get App/APK Info** - Retrieve application and APK details
- ✅ **Manage Versions** - List and manage APK versions
- ✅ **Create Apps/APKs** - Create new applications and APK versions
- ✅ **CI/CD Ready** - Environment variable support, exit codes, JSON output
- ✅ **Beginner Friendly** - No Java knowledge required (see BEGINNER_GUIDE.md)

## Quick Start

### 1. Install Prerequisites

- **Java 8 or higher** (Java 11 recommended)
- **Maven 3.6+**

#### Installing Java (if needed)

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-11-jdk
```

**MacOS:**
```bash
brew install openjdk@11
```

**Windows:**
Download from [Adoptium](https://adoptium.net/)

### 2. Install PAXStore SDK

The PAXStore SDK is not in Maven Central, so you need to install it locally first:

```bash
# Clone the PAXStore SDK
git clone https://github.com/PAXSTORE/paxstore-openapi-java-sdk.git
cd paxstore-openapi-java-sdk

# Install to local Maven repository
mvn clean install -DskipTests

# Return to CLI directory
cd ..
```

### 3. Build the CLI Tool

```bash
# Clone this repository
git clone https://github.com/RahmanBadru/paxstore-deployment-cli.git
cd paxstore-deployment-cli

# Build (Unix/Linux/Mac)
./build.sh

# OR Build (Windows)
build.bat
```

This creates `target/paxstore-cli.jar` - a single executable JAR with all dependencies.

### 4. Configure Credentials

Set environment variables (recommended for CI/CD):

```bash
export PAXSTORE_BASE_URL=https://api.whatspos.com/p-market-api
export PAXSTORE_API_KEY=your-api-key
export PAXSTORE_API_SECRET=your-api-secret
```

### 5. Run Commands

```bash
# Upload APK
java -jar target/paxstore-cli.jar upload \
  --apk-file path/to/app.apk \
  --app-name "MyApp" \
  --base-type N \
  --charge-type 0 \
  --models "A920,A930" \
  --categories "WL_PS,WL_SK" \
  --short-desc "Short description" \
  --description "Full description" \
  --release-notes "Release notes" \
  --icon icon.png

# Submit APK for approval
java -jar target/paxstore-cli.jar submit --apk-id 123456789

# Get app information
java -jar target/paxstore-cli.jar get-app-info \
  --package-name com.example.app \
  --app-name "MyApp"
```

## Commands

### upload

Upload an APK to PAXStore. This is the most important command for CI/CD.

```bash
java -jar target/paxstore-cli.jar upload \
  --apk-file path/to/app.apk \
  --app-name "MyApp" \
  --base-type N \
  --charge-type 0 \
  --models "A920,A930" \
  --categories "WL_PS,WL_SK" \
  --short-desc "Short description" \
  --description "Full description" \
  --release-notes "Release notes" \
  --screenshots "screenshot1.png,screenshot2.png,screenshot3.png" \
  --icon icon.png \
  --featured-image featured.png
```

**Options:**
- `--apk-file` (required): Path to APK file
- `--app-name` (required): Application name
- `--base-type` (required): Base type - N (Normal) or P (Parameter)
- `--charge-type` (required): Charge type - 0 (Free) or 1 (Paid)
- `--models` (required): Comma-separated terminal models (e.g., A920,A930)
- `--categories` (required): Comma-separated categories (e.g., WL_PS,WL_SK)
- `--short-desc` (required): Short description
- `--description` (required): Full description
- `--release-notes`: Release notes
- `--screenshots`: Comma-separated screenshot paths
- `--icon`: Icon file path (PNG/JPG)
- `--featured-image`: Featured image path (PNG/JPG)
- `--output`: Output format (human, json)
- `--quiet`, `-q`: Suppress output except errors
- `--dry-run`: Validate without uploading

### submit

Submit an APK for approval.

```bash
java -jar target/paxstore-cli.jar submit --apk-id 123456789
```

**Options:**
- `--apk-id` (required): APK ID to submit

### get-app-info

Get application information.

```bash
java -jar target/paxstore-cli.jar get-app-info \
  --package-name com.example.app \
  --app-name "MyApp"
```

**Options:**
- `--package-name` (required): Package name
- `--app-name` (required): Application name

### get-apk-info

Get APK information by ID.

```bash
java -jar target/paxstore-cli.jar get-apk-info --apk-id 123456789
```

**Options:**
- `--apk-id` (required): APK ID

### get-apk-versions

List all APK versions for an app.

```bash
java -jar target/paxstore-cli.jar get-apk-versions --app-id 123456
```

**Options:**
- `--app-id` (required): Application ID

### create-app

Create a new application.

```bash
java -jar target/paxstore-cli.jar create-app \
  --app-name "MyNewApp" \
  --app-key "ABC123XYZ456QWERTY12"
```

**Options:**
- `--app-name` (required): Application name
- `--app-key` (required): Application key

### create-apk

Create a new APK version for an existing app.

```bash
java -jar target/paxstore-cli.jar create-apk \
  --app-id 123456 \
  --apk-name "MyApp v1.0" \
  --apk-file path/to/app.apk \
  --apk-type N \
  --models "A920" \
  --categories "WL_PS" \
  --short-desc "Description" \
  --description "Full description" \
  --screenshots "screenshot1.png,screenshot2.png,screenshot3.png" \
  --icon icon.png
```

**Options:**
- `--app-id` (required): Application ID
- `--apk-name` (required): APK name
- `--apk-file` (required): Path to APK file
- `--apk-type` (required): APK type - N (Normal) or P (Parameter)
- `--models` (required): Comma-separated terminal models
- `--categories` (required): Comma-separated categories
- `--short-desc` (required): Short description
- `--description` (required): Full description
- `--screenshots`: Comma-separated screenshot paths
- `--icon`: Icon file path

### delete-apk

Delete an APK.

```bash
java -jar target/paxstore-cli.jar delete-apk --apk-id 123456789
```

**Options:**
- `--apk-id` (required): APK ID to delete

## Configuration

### Environment Variables (Recommended)

```bash
export PAXSTORE_BASE_URL=https://api.whatspos.com/p-market-api
export PAXSTORE_API_KEY=your-api-key
export PAXSTORE_API_SECRET=your-api-secret
```

### Command-Line Arguments

Override environment variables:

```bash
java -jar target/paxstore-cli.jar upload \
  --base-url https://api.whatspos.com/p-market-api \
  --api-key YOUR_KEY \
  --api-secret YOUR_SECRET \
  --apk-file app.apk ...
```

### Configuration File (Optional)

Create `paxstore-cli.properties` in the working directory:

```properties
paxstore.baseUrl=https://api.whatspos.com/p-market-api
paxstore.apiKey=your-api-key
paxstore.apiSecret=your-api-secret
```

**Priority:** Command-line args > Environment variables > Config file

## CI/CD Integration

### Bitbucket Pipelines

```yaml
image: maven:3.8.6-openjdk-11

pipelines:
  branches:
    main:
      - step:
          name: Build and Deploy to PAXStore
          caches:
            - maven
          script:
            # Build your Android APK
            - ./gradlew assembleRelease
            
            # Install PAXStore SDK
            - git clone https://github.com/PAXSTORE/paxstore-openapi-java-sdk.git
            - cd paxstore-openapi-java-sdk && mvn clean install -DskipTests && cd ..
            
            # Build CLI tool
            - mvn clean package
            
            # Upload to PAXStore
            - |
              java -jar target/paxstore-cli.jar upload \
                --apk-file app/build/outputs/apk/release/app-release.apk \
                --app-name "MyApp" \
                --base-type N \
                --charge-type 0 \
                --models "A920" \
                --categories "WL_PS" \
                --short-desc "Build ${BITBUCKET_BUILD_NUMBER}" \
                --description "Automated build from Bitbucket" \
                --release-notes "Build #${BITBUCKET_BUILD_NUMBER}" \
                --icon icon.png
```

**Set these in Bitbucket Repository Settings > Repository variables:**
- `PAXSTORE_BASE_URL`
- `PAXSTORE_API_KEY`
- `PAXSTORE_API_SECRET` (mark as secured)

### GitHub Actions

```yaml
name: Deploy to PAXStore

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
      
      - name: Install PAXStore SDK
        run: |
          git clone https://github.com/PAXSTORE/paxstore-openapi-java-sdk.git
          cd paxstore-openapi-java-sdk
          mvn clean install -DskipTests
          cd ..
      
      - name: Build CLI tool
        run: mvn clean package
      
      - name: Upload to PAXStore
        env:
          PAXSTORE_BASE_URL: ${{ secrets.PAXSTORE_BASE_URL }}
          PAXSTORE_API_KEY: ${{ secrets.PAXSTORE_API_KEY }}
          PAXSTORE_API_SECRET: ${{ secrets.PAXSTORE_API_SECRET }}
        run: |
          java -jar target/paxstore-cli.jar upload \
            --apk-file app.apk \
            --app-name "MyApp" \
            --base-type N \
            --charge-type 0 \
            --models "A920" \
            --categories "WL_PS" \
            --short-desc "Automated build" \
            --description "Build from GitHub Actions" \
            --release-notes "Automated deployment" \
            --icon icon.png
```

## Output Formats

### Human-Readable (Default)

```
✓ Success!
Message: APK uploaded successfully

Data:
123456789
```

### JSON

```bash
java -jar target/paxstore-cli.jar upload --output json ...
```

```json
{
  "success": true,
  "businessCode": 0,
  "message": "APK uploaded successfully",
  "data": 123456789
}
```

## Exit Codes

- `0` - Success
- `1` - Validation error (bad input)
- `2` - API error (operation failed)

Use in scripts:

```bash
if java -jar target/paxstore-cli.jar upload ... ; then
  echo "Upload successful"
else
  echo "Upload failed"
  exit 1
fi
```

## Error Handling

The CLI provides clear, actionable error messages:

```
✗ Validation Error: APK file not found or not readable: app.apk
```

```
✗ Error: Operation failed
Business Code: 1213
Message: App name is mandatory
```

## Troubleshooting

### Build fails with "Could not resolve dependencies"

The PAXStore SDK must be installed locally first:

```bash
git clone https://github.com/PAXSTORE/paxstore-openapi-java-sdk.git
cd paxstore-openapi-java-sdk
mvn clean install -DskipTests
```

### "Base URL is required" error

Set credentials using environment variables:

```bash
export PAXSTORE_BASE_URL=https://api.whatspos.com/p-market-api
export PAXSTORE_API_KEY=your-api-key
export PAXSTORE_API_SECRET=your-api-secret
```

### File not found errors

Use absolute or relative paths correctly:

```bash
# Absolute path
--apk-file /home/user/project/app.apk

# Relative path (from working directory)
--apk-file ./build/app.apk
```

### APK validation fails

Ensure:
- File exists and is readable
- File has .apk extension
- File is a valid APK (not corrupted)

### Screenshots/Icon validation fails

Ensure:
- Files exist and are readable
- Files are PNG, JPG, or JPEG format
- Paths are comma-separated without spaces (or quoted)

## Examples

See the `examples/` directory for complete working examples:
- `basic-upload.sh` - Simple upload example
- `full-pipeline.sh` - Complete workflow
- `bitbucket-example.yml` - Bitbucket Pipelines config
- `github-example.yml` - GitHub Actions config
- `gitlab-example.yml` - GitLab CI config

## For Non-Java Developers

See [BEGINNER_GUIDE.md](BEGINNER_GUIDE.md) for a step-by-step guide that assumes no Java knowledge.

## License

MIT License - see LICENSE file for details

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## Support

- Issues: [GitHub Issues](https://github.com/RahmanBadru/paxstore-deployment-cli/issues)
- PAXStore Documentation: [PAXStore Developer Portal](https://developer.pax.com/)
- PAXStore SDK: [GitHub](https://github.com/PAXSTORE/paxstore-openapi-java-sdk)