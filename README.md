# PAXStore CLI - Complete Guide for Everyone

**A command-line tool to upload Android APKs to PAXStore automatically. No Java coding required!**

---

## Table of Contents

1. [What is This Tool?](#1-what-is-this-tool)
2. [Prerequisites](#2-prerequisites)
3. [Quick Start (5 Minutes)](#3-quick-start-5-minutes)
4. [Installation (Step-by-Step)](#4-installation-step-by-step)
5. [Configuration](#5-configuration)
6. [All Available Commands](#6-all-available-commands)
7. [Using in Bitbucket Pipelines](#7-using-in-bitbucket-pipelines)
8. [Using in GitHub Actions](#8-using-in-github-actions)
9. [Troubleshooting](#9-troubleshooting)
10. [Understanding Java Basics](#10-understanding-java-basics)
11. [How the SDK Integration Works](#11-how-the-sdk-integration-works)
12. [Real-World Examples](#12-real-world-examples)

---

## 1. What is This Tool?

### The Simple Explanation

Imagine you have an Android app (APK file) and you need to upload it to PAXStore. You could do it manually through a website, but that's tedious and doesn't work in automated build systems.

**This tool is like a robot assistant** that uploads your APK to PAXStore for you. You just tell it where your APK file is, and it handles everything else.

### What You Can Do With It

- ✅ Upload APK files to PAXStore automatically
- ✅ Submit APKs for approval
- ✅ Get information about your apps and APKs
- ✅ Manage multiple versions of your apps
- ✅ Use it in CI/CD pipelines (Bitbucket, GitHub Actions, GitLab)
- ✅ Works with environment variables (perfect for automation)

### Who Should Use This?

- **DevOps Engineers** setting up CI/CD pipelines
- **Android Developers** who want to automate deployments
- **Build Engineers** managing automated releases
- **Anyone** who needs to upload APKs to PAXStore repeatedly

---

## 2. Prerequisites

### What You Need

Before using this tool, you need three things:

#### 2.1 Java (Version 8 or higher)

**What is Java?** Think of it like Microsoft Word or Chrome - it's software that runs other software. This tool is written in Java, so you need Java installed to run it.

**Do I need to know Java programming?** NO! You just need Java installed on your computer. You won't write any Java code.

##### Installing Java on Ubuntu/Debian Linux

```bash
# Update your package list (like updating the "app store")
sudo apt update

# Install Java 11
sudo apt install openjdk-11-jdk

# Verify it worked (you should see version info)
java -version
```

**Expected output:**
```
openjdk version "11.0.20" 2023-07-18
OpenJDK Runtime Environment (build 11.0.20+8-post-Ubuntu-1ubuntu120.04)
OpenJDK 64-Bit Server VM (build 11.0.20+8-post-Ubuntu-1ubuntu120.04, mixed mode)
```

##### Installing Java on macOS

```bash
# Install Homebrew if you don't have it
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Java 11
brew install openjdk@11

# Add Java to your PATH (so your computer can find it)
echo 'export PATH="/usr/local/opt/openjdk@11/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# Verify it worked
java -version
```

##### Installing Java on Windows

1. Go to [Adoptium.net](https://adoptium.net/)
2. Download **Eclipse Temurin 11** (Windows x64 installer)
3. Run the installer
4. Check "Set JAVA_HOME variable" during installation
5. Open Command Prompt and verify:
   ```cmd
   java -version
   ```

#### 2.2 Maven (Version 3.6 or higher)

**What is Maven?** It's a build tool that helps compile and package Java programs. Think of it like a recipe book that knows how to assemble all the pieces.

##### Installing Maven on Ubuntu/Debian

```bash
sudo apt update
sudo apt install maven

# Verify installation
mvn -version
```

##### Installing Maven on macOS

```bash
brew install maven

# Verify installation
mvn -version
```

##### Installing Maven on Windows

1. Download from [Maven Download Page](https://maven.apache.org/download.cgi)
2. Extract to `C:\Program Files\Apache\maven`
3. Add to PATH:
   - Open "Environment Variables" in System Settings
   - Add `C:\Program Files\Apache\maven\bin` to PATH
4. Verify in Command Prompt:
   ```cmd
   mvn -version
   ```

#### 2.3 PAXStore API Credentials

You need three pieces of information from PAXStore:

1. **Base URL** - The API endpoint (usually `https://api.whatspos.com/p-market-api`)
2. **API Key** - Your unique identifier (like a username)
3. **API Secret** - Your password (keep it secret!)

**Where to get these?** Contact your PAXStore account manager or check the PAXStore Developer Portal.

---

## 3. Quick Start (5 Minutes)

If you already have Java and Maven installed, here's the fastest way to get started:

```bash
# 1. Clone this repository
git clone https://github.com/RahmanBadru/paxstore-deployment-cli.git
cd paxstore-deployment-cli

# 2. Build the tool (this creates a single JAR file)
mvn clean package

# 3. Set your credentials (replace with your actual values)
export PAXSTORE_BASE_URL="https://api.whatspos.com/p-market-api"
export PAXSTORE_API_KEY="your-api-key-here"
export PAXSTORE_API_SECRET="your-secret-here"

# 4. Upload an APK (replace with your actual file paths)
java -jar target/paxstore-cli.jar upload \
  --apk-file /path/to/your/app.apk \
  --app-name "My Awesome App" \
  --base-type N \
  --charge-type 0 \
  --models "A920,A930" \
  --categories "WL_PS" \
  --short-desc "A great payment app" \
  --description "This app helps process payments efficiently" \
  --icon /path/to/icon.png
```

**What just happened?**
1. You downloaded the tool's source code
2. Maven compiled it into a single executable file (`paxstore-cli.jar`)
3. You told it your PAXStore credentials
4. You uploaded an APK to PAXStore

---

## 4. Installation (Step-by-Step)

### Step 1: Verify Java is Installed

```bash
java -version
```

If you see an error like "java: command not found", go back to section 2.1 and install Java.

### Step 2: Verify Maven is Installed

```bash
mvn -version
```

If you see an error, go back to section 2.2 and install Maven.

### Step 3: Download This Tool

**Option A: Using Git (Recommended)**

```bash
git clone https://github.com/RahmanBadru/paxstore-deployment-cli.git
cd paxstore-deployment-cli
```

**Option B: Download ZIP**

1. Go to https://github.com/RahmanBadru/paxstore-deployment-cli
2. Click "Code" → "Download ZIP"
3. Extract the ZIP file
4. Open terminal/command prompt and navigate to the extracted folder

### Step 4: Build the Tool

```bash
# This command does several things:
# - Downloads required libraries (PAXStore SDK and others)
# - Compiles the Java code
# - Packages everything into one JAR file
mvn clean package
```

**This will take 1-3 minutes the first time** because Maven downloads dependencies from the internet.

**Expected output:**
```
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 45.234 s
[INFO] Finished at: 2024-01-15T10:30:45Z
[INFO] ------------------------------------------------------------------------
```

**What it created:**
- `target/paxstore-cli.jar` - This is the tool! A single file ~20-30 MB in size.

### Step 5: Verify the Build

```bash
java -jar target/paxstore-cli.jar --help
```

You should see a list of available commands. If you do, **congratulations!** The tool is ready to use.

---

## 5. Configuration

The tool needs three pieces of information to connect to PAXStore:
1. Base URL
2. API Key
3. API Secret

You can provide these in three ways. The tool checks them in this order:

### Method 1: Command-Line Arguments (Highest Priority)

Pass credentials directly when running commands:

```bash
java -jar target/paxstore-cli.jar upload \
  --base-url "https://api.whatspos.com/p-market-api" \
  --api-key "your-key" \
  --api-secret "your-secret" \
  --apk-file app.apk \
  # ... other options
```

**When to use:** Testing or one-time uploads

### Method 2: Environment Variables (Recommended for CI/CD)

Set environment variables in your terminal:

**Linux/Mac:**
```bash
export PAXSTORE_BASE_URL="https://api.whatspos.com/p-market-api"
export PAXSTORE_API_KEY="your-key-here"
export PAXSTORE_API_SECRET="your-secret-here"
```

**Windows (Command Prompt):**
```cmd
set PAXSTORE_BASE_URL=https://api.whatspos.com/p-market-api
set PAXSTORE_API_KEY=your-key-here
set PAXSTORE_API_SECRET=your-secret-here
```

**Windows (PowerShell):**
```powershell
$env:PAXSTORE_BASE_URL="https://api.whatspos.com/p-market-api"
$env:PAXSTORE_API_KEY="your-key-here"
$env:PAXSTORE_API_SECRET="your-secret-here"
```

**When to use:** Local development, CI/CD pipelines

**To make permanent** (Linux/Mac), add to your `~/.bashrc` or `~/.zshrc`:
```bash
echo 'export PAXSTORE_BASE_URL="https://api.whatspos.com/p-market-api"' >> ~/.bashrc
echo 'export PAXSTORE_API_KEY="your-key"' >> ~/.bashrc
echo 'export PAXSTORE_API_SECRET="your-secret"' >> ~/.bashrc
source ~/.bashrc
```

### Method 3: Configuration File

Create a file named `paxstore-cli.properties` in your working directory:

```properties
paxstore.baseUrl=https://api.whatspos.com/p-market-api
paxstore.apiKey=your-key-here
paxstore.apiSecret=your-secret-here
```

**When to use:** Local development with multiple projects

**⚠️ Security Warning:** Never commit this file to Git! Add it to `.gitignore`:
```bash
echo "paxstore-cli.properties" >> .gitignore
```

---

## 6. All Available Commands

### 6.1 upload - Upload an APK

**What it does:** Uploads an APK file to PAXStore and creates/updates the app listing.

**When to use:** This is your main command for CI/CD. Use it every time you build a new version.

**Full Example:**
```bash
java -jar target/paxstore-cli.jar upload \
  --apk-file build/outputs/apk/release/app-release.apk \
  --app-name "Payment Terminal App" \
  --base-type N \
  --charge-type 0 \
  --models "A920,A930,A50,A77" \
  --categories "WL_PS,WL_SK" \
  --short-desc "Fast and secure payment processing" \
  --description "This app provides comprehensive payment processing capabilities for PAX terminals. Features include contactless payments, EMV support, and receipt printing." \
  --release-notes "Version 2.1.0: Added support for QR code payments, fixed bug in receipt printer, improved transaction speed" \
  --screenshots "screenshots/screen1.png,screenshots/screen2.png,screenshots/screen3.png" \
  --icon assets/icon.png \
  --featured-image assets/featured.png
```

**Required Options:**

| Option | Description | Example |
|--------|-------------|---------|
| `--apk-file` | Path to your APK file | `build/app-release.apk` |
| `--app-name` | Display name of your app | `"My Payment App"` |
| `--base-type` | N = Normal app, P = Parameterized app | `N` |
| `--charge-type` | 0 = Free, 1 = Paid | `0` |
| `--models` | Comma-separated terminal models | `"A920,A930"` |
| `--categories` | Comma-separated PAXStore categories | `"WL_PS,WL_SK"` |
| `--short-desc` | Brief description (< 200 chars) | `"Payment app"` |
| `--description` | Full description (HTML supported) | `"Complete description..."` |

**Optional Options:**

| Option | Description | Example |
|--------|-------------|---------|
| `--release-notes` | What's new in this version | `"Fixed bugs, added features"` |
| `--screenshots` | Comma-separated image paths | `"img1.png,img2.png,img3.png"` |
| `--icon` | App icon (PNG/JPG, square) | `"icon.png"` |
| `--featured-image` | Banner image | `"banner.png"` |
| `--output` | Output format: `human` or `json` | `json` |
| `--quiet` or `-q` | Only show errors | (flag, no value) |
| `--dry-run` | Validate without uploading | (flag, no value) |

**Success Output:**
```
✓ Success!
Message: APK uploaded successfully

Data:
123456789

The APK ID is: 123456789
```

**Using the APK ID:** Save this number! You'll need it to submit the APK for approval.

### 6.2 submit - Submit APK for Approval

**What it does:** Submits an uploaded APK to PAXStore for review and approval.

**When to use:** After uploading, submit it for PAXStore team to review and publish.

**Example:**
```bash
java -jar target/paxstore-cli.jar submit --apk-id 123456789
```

**In CI/CD, capture the ID from upload:**
```bash
# Upload and save the APK ID
APK_ID=$(java -jar target/paxstore-cli.jar upload --output json ... | jq -r '.data')

# Submit for approval
java -jar target/paxstore-cli.jar submit --apk-id $APK_ID
```

### 6.3 get-app-info - Get Application Information

**What it does:** Retrieves information about an app from PAXStore.

**When to use:** To check if an app exists, get its ID, or view its details.

**Example:**
```bash
java -jar target/paxstore-cli.jar get-app-info \
  --package-name "com.mycompany.paymentapp" \
  --app-name "Payment Terminal App"
```

**Output:**
```json
{
  "appId": 123456,
  "appName": "Payment Terminal App",
  "packageName": "com.mycompany.paymentapp",
  "status": "active",
  "versions": [...],
  ...
}
```

### 6.4 get-apk-info - Get APK Information

**What it does:** Gets detailed information about a specific APK version.

**Example:**
```bash
java -jar target/paxstore-cli.jar get-apk-info --apk-id 123456789
```

### 6.5 get-apk-versions - List All APK Versions

**What it does:** Lists all versions of an app.

**When to use:** To see version history or find specific version IDs.

**Example:**
```bash
java -jar target/paxstore-cli.jar get-apk-versions --app-id 123456
```

### 6.6 create-app - Create New Application

**What it does:** Creates a new app entry in PAXStore (without uploading an APK).

**Example:**
```bash
java -jar target/paxstore-cli.jar create-app \
  --app-name "New Payment App" \
  --app-key "YOUR_UNIQUE_APP_KEY_123"
```

### 6.7 create-apk - Create New APK Version

**What it does:** Adds a new APK version to an existing app.

**Example:**
```bash
java -jar target/paxstore-cli.jar create-apk \
  --app-id 123456 \
  --apk-name "Payment App v2.0" \
  --apk-file build/app-v2.apk \
  --apk-type N \
  --models "A920" \
  --categories "WL_PS" \
  --short-desc "Version 2.0 with new features" \
  --description "Major update with improved UI" \
  --icon icon.png
```

### 6.8 delete-apk - Delete an APK

**What it does:** Removes an APK from PAXStore.

**⚠️ Warning:** This is permanent and cannot be undone!

**Example:**
```bash
java -jar target/paxstore-cli.jar delete-apk --apk-id 123456789
```

---

## 7. Using in Bitbucket Pipelines

### Complete Working Example

Create `bitbucket-pipelines.yml` in your repository root:

```yaml
image: maven:3.8-openjdk-11

pipelines:
  default:
    - step:
        name: Build and Deploy to PAXStore
        caches:
          - maven
        script:
          # 1. Build your Android app (example with Gradle)
          - apt-get update && apt-get install -y android-sdk
          - ./gradlew assembleRelease
          
          # 2. Build the PAXStore CLI tool
          - mvn clean package
          
          # 3. Upload to PAXStore
          - |
            java -jar target/paxstore-cli.jar upload \
              --apk-file app/build/outputs/apk/release/app-release.apk \
              --app-name "My Payment App" \
              --base-type N \
              --charge-type 0 \
              --models "A920,A930" \
              --categories "WL_PS" \
              --short-desc "Build ${BITBUCKET_BUILD_NUMBER}" \
              --description "Automated build from Bitbucket Pipelines" \
              --release-notes "Build #${BITBUCKET_BUILD_NUMBER} - ${BITBUCKET_COMMIT}" \
              --icon app/src/main/res/mipmap-xxxhdpi/ic_launcher.png
          
          # 4. Submit for approval (capture ID from previous command)
          - |
            APK_ID=$(java -jar target/paxstore-cli.jar upload --output json ... | jq -r '.data')
            java -jar target/paxstore-cli.jar submit --apk-id $APK_ID
```

### Setting Up Credentials in Bitbucket

1. Go to your repository in Bitbucket
2. Click **Settings** → **Repository variables**
3. Add these variables:
   - `PAXSTORE_BASE_URL` = `https://api.whatspos.com/p-market-api`
   - `PAXSTORE_API_KEY` = Your API key
   - `PAXSTORE_API_SECRET` = Your API secret (check "Secured")

### Testing Your Pipeline

1. Commit the `bitbucket-pipelines.yml` file
2. Push to Bitbucket
3. Go to **Pipelines** in Bitbucket to see the build
4. Check for success message: "APK uploaded successfully"

---

## 8. Using in GitHub Actions

### Complete Working Example

Create `.github/workflows/deploy-to-paxstore.yml`:

```yaml
name: Deploy to PAXStore

on:
  push:
    branches: [ main, master ]
  workflow_dispatch:  # Allows manual triggering

jobs:
  deploy:
    runs-on: ubuntu-latest
    
    steps:
      # 1. Checkout code
      - name: Checkout repository
        uses: actions/checkout@v3
      
      # 2. Set up Java
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          cache: 'maven'
      
      # 3. Build Android app (if you have one in this repo)
      - name: Build Android APK
        run: |
          cd android-app
          ./gradlew assembleRelease
      
      # 4. Build PAXStore CLI tool
      - name: Build PAXStore CLI
        run: mvn clean package
      
      # 5. Upload to PAXStore
      - name: Upload APK to PAXStore
        env:
          PAXSTORE_BASE_URL: ${{ secrets.PAXSTORE_BASE_URL }}
          PAXSTORE_API_KEY: ${{ secrets.PAXSTORE_API_KEY }}
          PAXSTORE_API_SECRET: ${{ secrets.PAXSTORE_API_SECRET }}
        run: |
          java -jar target/paxstore-cli.jar upload \
            --apk-file android-app/app/build/outputs/apk/release/app-release.apk \
            --app-name "My Payment App" \
            --base-type N \
            --charge-type 0 \
            --models "A920,A930" \
            --categories "WL_PS" \
            --short-desc "Automated build" \
            --description "Build from GitHub Actions - Run #${{ github.run_number }}" \
            --release-notes "Commit: ${{ github.sha }}" \
            --icon android-app/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png \
            --output json > upload-result.json
      
      # 6. Submit for approval
      - name: Submit APK for approval
        env:
          PAXSTORE_BASE_URL: ${{ secrets.PAXSTORE_BASE_URL }}
          PAXSTORE_API_KEY: ${{ secrets.PAXSTORE_API_KEY }}
          PAXSTORE_API_SECRET: ${{ secrets.PAXSTORE_API_SECRET }}
        run: |
          APK_ID=$(cat upload-result.json | jq -r '.data')
          echo "Submitting APK ID: $APK_ID"
          java -jar target/paxstore-cli.jar submit --apk-id $APK_ID
      
      # 7. Save APK ID as artifact
      - name: Upload result as artifact
        uses: actions/upload-artifact@v3
        with:
          name: paxstore-upload-result
          path: upload-result.json
```

### Setting Up Secrets in GitHub

1. Go to your repository on GitHub
2. Click **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**
4. Add these secrets:
   - `PAXSTORE_BASE_URL`
   - `PAXSTORE_API_KEY`
   - `PAXSTORE_API_SECRET`

### Testing Your Workflow

1. Commit the workflow file
2. Push to GitHub
3. Go to **Actions** tab
4. You should see your workflow running
5. Click on it to see detailed logs

---

## 9. Troubleshooting

### Common Errors and Solutions

#### Error: "java: command not found"

**Problem:** Java is not installed or not in your PATH.

**Solution:**
```bash
# Verify Java installation
which java

# If nothing appears, install Java (see section 2.1)
sudo apt install openjdk-11-jdk  # Ubuntu/Debian

# Verify again
java -version
```

#### Error: "mvn: command not found"

**Problem:** Maven is not installed or not in your PATH.

**Solution:**
```bash
# Install Maven (see section 2.2)
sudo apt install maven  # Ubuntu/Debian

# Verify
mvn -version
```

#### Error: "Could not resolve dependencies"

**Problem:** Maven cannot download the PAXStore SDK from JitPack.

**Solution 1 - Check internet connection:**
```bash
ping jitpack.io
```

**Solution 2 - Force Maven to update:**
```bash
mvn clean package -U
```

**Solution 3 - Clear Maven cache:**
```bash
rm -rf ~/.m2/repository/com/github/PAXSTORE
mvn clean package
```

#### Error: "Base URL is required"

**Problem:** Credentials are not configured.

**Solution:** Set environment variables:
```bash
export PAXSTORE_BASE_URL="https://api.whatspos.com/p-market-api"
export PAXSTORE_API_KEY="your-key"
export PAXSTORE_API_SECRET="your-secret"

# Verify they're set
echo $PAXSTORE_BASE_URL
```

#### Error: "APK file not found"

**Problem:** The path to your APK is incorrect.

**Solution:**
```bash
# Check if file exists
ls -la /path/to/your/app.apk

# Use absolute path
--apk-file /home/user/project/build/app.apk

# Or relative path from current directory
--apk-file ./build/app.apk
```

#### Error: "File is not an APK"

**Problem:** The file doesn't have `.apk` extension or is corrupted.

**Solution:**
```bash
# Check file type
file your-app.apk

# Should output: "Android package (APK)"
# If not, your file is not a valid APK
```

#### Error: "Icon must be an image file"

**Problem:** Icon file is not PNG, JPG, or JPEG.

**Solution:**
```bash
# Check file type
file your-icon.png

# Convert if needed (requires ImageMagick)
convert your-icon.jpg your-icon.png
```

#### Error: "BUILD FAILURE" during mvn package

**Problem:** Compilation errors or missing dependencies.

**Solution:**
```bash
# Clean and rebuild
mvn clean

# Build with full debug output
mvn package -X

# Check Java version (must be 8+)
java -version
```

#### API Errors (Business Code errors)

**Error:** "Business Code: 1213 - App name is mandatory"

**Solution:** Check all required parameters are provided and not empty.

**Error:** "Business Code: 401 - Unauthorized"

**Solution:** Your API credentials are incorrect. Double-check:
```bash
# Print your credentials (be careful with secrets!)
echo $PAXSTORE_API_KEY
echo $PAXSTORE_API_SECRET
```

---

## 10. Understanding Java Basics

### What is Java?

Java is a programming language, but you don't need to learn it to use this tool. Here's what you need to know:

#### The `java` Command

When you type `java -jar target/paxstore-cli.jar`, here's what happens:

1. **`java`** - Calls the Java Runtime Environment (JRE)
2. **`-jar`** - Tells Java you're running a JAR file
3. **`target/paxstore-cli.jar`** - The program to run
4. **Everything after** - Arguments passed to the program

It's similar to:
- `python script.py` (Python)
- `node app.js` (Node.js)
- `./program` (Compiled C/C++)

#### What is a JAR file?

JAR = **J**ava **AR**chive

Think of it like a ZIP file that contains:
- Compiled Java code (`.class` files)
- Libraries and dependencies
- Resources (images, configs, etc.)

**Key point:** A JAR is executable - you can run it directly with `java -jar`.

#### What is Maven?

Maven is a build tool. When you run `mvn package`, Maven:

1. **Downloads dependencies** - Gets libraries from the internet (like npm, pip)
2. **Compiles code** - Turns Java source (`.java`) into bytecode (`.class`)
3. **Runs tests** - Executes test cases (if any)
4. **Packages** - Bundles everything into a JAR file

The `pom.xml` file is like `package.json` (Node) or `requirements.txt` (Python) - it lists dependencies.

### Java Environment Variables

- **`JAVA_HOME`** - Points to Java installation directory
- **`PATH`** - Includes Java bin directory so you can run `java` from anywhere

Check them:
```bash
echo $JAVA_HOME    # Should show /usr/lib/jvm/java-11-openjdk-amd64 or similar
echo $PATH          # Should include $JAVA_HOME/bin
```

---

## 11. How the SDK Integration Works

### Architecture Overview

```
┌─────────────────┐
│   Your Computer │
│                 │
│  paxstore-cli   │  ← This tool (Java JAR)
│      ↓          │
│  Developer SDK  │  ← PAXStore SDK (from JitPack)
│      ↓          │
│   HTTP/REST     │  ← Makes API calls
└────────┬────────┘
         │
         ↓
┌─────────────────┐
│  PAXStore API   │  ← PAXStore servers
│                 │
│  Upload & Store │
│  APK Files      │
└─────────────────┘
```

### What Happens When You Upload

1. **You run the command:**
   ```bash
   java -jar target/paxstore-cli.jar upload --apk-file app.apk ...
   ```

2. **The CLI validates:**
   - Checks if APK file exists
   - Validates icon and screenshots
   - Verifies all required parameters

3. **The CLI reads files:**
   - Uses `FileUtils.readFile()` from SDK
   - Converts files to `UploadedFileContent` objects
   - Encodes as base64 or multipart

4. **The SDK sends HTTP request:**
   ```
   POST https://api.whatspos.com/p-market-api/developer/apk
   Headers:
     - Authorization: (your API key + secret)
     - Content-Type: multipart/form-data
   Body:
     - appFile: (APK content)
     - iconFile: (icon content)
     - appName: "My App"
     - ... (other parameters)
   ```

5. **PAXStore receives and processes:**
   - Validates APK signature
   - Extracts APK metadata (version, package name)
   - Stores APK in their storage
   - Returns APK ID

6. **You get the response:**
   ```
   ✓ Success!
   APK ID: 123456789
   ```

### The Dependency Chain

```
paxstore-cli.jar
├── PAXStore Developer SDK (from JitPack)
│   ├── DeveloperApi.java
│   ├── FileUtils.java
│   ├── DTOs (CreateApkRequest, Result, etc.)
│   └── HTTP Client
├── picocli (CLI framework)
├── gson (JSON parsing)
└── slf4j (logging)
```

### JitPack Integration

**What is JitPack?** A Maven repository that builds Java libraries directly from GitHub.

In `pom.xml`:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.PAXSTORE</groupId>
        <artifactId>paxstore-develop-sdk</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
</dependencies>
```

When you run `mvn package`:
1. Maven contacts jitpack.io
2. JitPack checks if it has the SDK cached
3. If not, JitPack clones `PAXSTORE/paxstore-develop-sdk` from GitHub
4. JitPack builds it and caches the result
5. Maven downloads the built JAR
6. Maven includes it in your `paxstore-cli.jar`

---

## 12. Real-World Examples

### Example 1: Simple Upload in CI/CD

```bash
#!/bin/bash
# simple-upload.sh

# Exit on any error
set -e

# Build your Android app first
./gradlew assembleRelease

# Upload to PAXStore
java -jar target/paxstore-cli.jar upload \
  --apk-file app/build/outputs/apk/release/app-release.apk \
  --app-name "Payment App" \
  --base-type N \
  --charge-type 0 \
  --models "A920" \
  --categories "WL_PS" \
  --short-desc "Payment processing app" \
  --description "Full-featured payment app" \
  --icon app/src/main/res/mipmap-xxxhdpi/ic_launcher.png

echo "✓ Upload complete!"
```

### Example 2: Upload and Submit Pipeline

```bash
#!/bin/bash
# upload-and-submit.sh

set -e

echo "Building APK..."
./gradlew assembleRelease

echo "Uploading to PAXStore..."
RESULT=$(java -jar target/paxstore-cli.jar upload \
  --output json \
  --apk-file app/build/outputs/apk/release/app-release.apk \
  --app-name "Payment App" \
  --base-type N \
  --charge-type 0 \
  --models "A920,A930" \
  --categories "WL_PS" \
  --short-desc "Payment app v1.2.3" \
  --description "Bug fixes and improvements" \
  --icon icon.png)

# Extract APK ID from JSON
APK_ID=$(echo $RESULT | jq -r '.data')

if [ "$APK_ID" = "null" ] || [ -z "$APK_ID" ]; then
  echo "✗ Upload failed!"
  echo $RESULT | jq
  exit 1
fi

echo "✓ Upload successful! APK ID: $APK_ID"

echo "Submitting for approval..."
java -jar target/paxstore-cli.jar submit --apk-id $APK_ID

echo "✓ Submitted for approval!"
echo "Check PAXStore dashboard for approval status."
```

### Example 3: Multi-Environment Deployment

```bash
#!/bin/bash
# deploy-multi-env.sh

ENVIRONMENT=$1  # dev, staging, or prod

if [ -z "$ENVIRONMENT" ]; then
  echo "Usage: ./deploy-multi-env.sh [dev|staging|prod]"
  exit 1
fi

# Set environment-specific variables
case $ENVIRONMENT in
  dev)
    export PAXSTORE_BASE_URL="https://dev-api.whatspos.com/p-market-api"
    APP_NAME="Payment App (Dev)"
    ;;
  staging)
    export PAXSTORE_BASE_URL="https://staging-api.whatspos.com/p-market-api"
    APP_NAME="Payment App (Staging)"
    ;;
  prod)
    export PAXSTORE_BASE_URL="https://api.whatspos.com/p-market-api"
    APP_NAME="Payment App"
    ;;
  *)
    echo "Invalid environment: $ENVIRONMENT"
    exit 1
    ;;
esac

echo "Deploying to $ENVIRONMENT..."

java -jar target/paxstore-cli.jar upload \
  --apk-file app.apk \
  --app-name "$APP_NAME" \
  --base-type N \
  --charge-type 0 \
  --models "A920" \
  --categories "WL_PS" \
  --short-desc "Payment app for $ENVIRONMENT" \
  --description "Payment app deployed to $ENVIRONMENT environment" \
  --icon icon.png

echo "✓ Deployed to $ENVIRONMENT successfully!"
```

### Example 4: Conditional Upload (Only on Tag)

```yaml
# .github/workflows/deploy-on-tag.yml

name: Deploy on Tag

on:
  push:
    tags:
      - 'v*'  # Triggers on tags like v1.0.0, v2.1.3

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
      
      - name: Extract version from tag
        id: get_version
        run: echo "VERSION=${GITHUB_REF#refs/tags/v}" >> $GITHUB_OUTPUT
      
      - name: Build CLI
        run: mvn clean package
      
      - name: Upload to PAXStore
        env:
          PAXSTORE_BASE_URL: ${{ secrets.PAXSTORE_BASE_URL }}
          PAXSTORE_API_KEY: ${{ secrets.PAXSTORE_API_KEY }}
          PAXSTORE_API_SECRET: ${{ secrets.PAXSTORE_API_SECRET }}
        run: |
          java -jar target/paxstore-cli.jar upload \
            --apk-file app.apk \
            --app-name "Payment App" \
            --base-type N \
            --charge-type 0 \
            --models "A920" \
            --categories "WL_PS" \
            --short-desc "Release v${{ steps.get_version.outputs.VERSION }}" \
            --description "Official release" \
            --release-notes "Release notes for v${{ steps.get_version.outputs.VERSION }}" \
            --icon icon.png
      
      - name: Create GitHub Release
        uses: actions/create-release@v1
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
        with:
          tag_name: ${{ github.ref }}
          release_name: Release ${{ steps.get_version.outputs.VERSION }}
          body: Deployed to PAXStore
```

---

## Additional Resources

### Getting Help

- **Issue Tracker:** [GitHub Issues](https://github.com/RahmanBadru/paxstore-deployment-cli/issues)
- **Beginner's Guide:** See [BEGINNER_GUIDE.md](BEGINNER_GUIDE.md) for even more detailed explanations
- **PAXStore Documentation:** [PAXStore Developer Portal](https://developer.pax.com/)

### Related Links

- [PAXStore Developer SDK Repository](https://github.com/PAXSTORE/paxstore-develop-sdk)
- [Java SE Downloads](https://www.oracle.com/java/technologies/downloads/)
- [Maven Download](https://maven.apache.org/download.cgi)
- [JitPack Documentation](https://jitpack.io/docs/)

---

## License

MIT License - See LICENSE file for details

## Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

---

**Made with ❤️ for developers who just want things to work.**

If this tool helped you, consider giving it a ⭐ on GitHub!
