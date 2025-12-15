# PAXStore CLI - Complete Beginner's Guide

This guide is for developers who have **little to no Java experience** and need to use this tool in their CI/CD pipeline.

## What is This Tool?

This is a **command-line tool** (CLI) that lets you upload Android APKs to PAXStore automatically. Think of it as a robot that talks to PAXStore's API so you don't have to write code.

## What is Java and Why Do We Need It?

**Java** is a programming language. This tool is written in Java, so you need Java installed to run it. Don't worry - you don't need to write any Java code!

Think of it like this:
- Your APK is like a letter you want to mail
- This CLI tool is like the postal worker
- Java is the vehicle the postal worker uses

## Step 1: Install Java (One-Time Setup)

### On Ubuntu/Debian Linux

```bash
# Update package list
sudo apt update

# Install Java 11
sudo apt install openjdk-11-jdk

# Verify installation
java -version
```

You should see something like:
```
openjdk version "11.0.x"
```

### On MacOS

```bash
# Install Homebrew if you don't have it
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Java
brew install openjdk@11

# Verify installation
java -version
```

### On Windows

1. Go to [Adoptium.net](https://adoptium.net/)
2. Download "Temurin 11 (LTS)" for Windows
3. Run the installer
4. Open Command Prompt and type: `java -version`

## Step 2: Install Maven (One-Time Setup)

**Maven** is a build tool for Java projects. It's like npm for Node.js or pip for Python.

### On Ubuntu/Debian

```bash
sudo apt install maven
mvn -version
```

### On MacOS

```bash
brew install maven
mvn -version
```

### On Windows

1. Download from [Maven.apache.org](https://maven.apache.org/download.cgi)
2. Extract to `C:\Program Files\Maven`
3. Add `C:\Program Files\Maven\bin` to your PATH
4. Open new Command Prompt: `mvn -version`

## Step 3: Install PAXStore SDK (One-Time Setup)

The PAXStore SDK is a library this tool depends on. You need to install it to your computer once.

```bash
# Go to your home directory (or any directory you like)
cd ~

# Download the PAXStore SDK
git clone https://github.com/PAXSTORE/paxstore-openapi-java-sdk.git

# Go into the SDK directory
cd paxstore-openapi-java-sdk

# Install it (this takes 1-2 minutes)
mvn clean install -DskipTests

# You should see "BUILD SUCCESS" at the end
```

**What does this do?** It downloads the PAXStore SDK code and installs it to your computer's local Maven repository (usually at `~/.m2/repository`).

## Step 4: Download and Build This CLI Tool

```bash
# Go back to your projects directory
cd ~/projects  # or wherever you keep your code

# Clone this repository
git clone https://github.com/RahmanBadru/paxstore-deployment-cli.git

# Go into the project
cd paxstore-deployment-cli

# Build it (Unix/Linux/Mac)
./build.sh

# OR on Windows
build.bat
```

**What happened?** Maven downloaded all dependencies and created a single file called `paxstore-cli.jar` in the `target/` directory. This JAR file contains everything you need - you can copy it anywhere!

## Step 5: Set Up Your Credentials

You need three pieces of information from PAXStore:
1. **Base URL** - Usually `https://api.whatspos.com/p-market-api`
2. **API Key** - Your unique API key from PAXStore
3. **API Secret** - Your secret key from PAXStore

### Method 1: Environment Variables (Recommended)

**On Unix/Linux/Mac:**

Add to your `~/.bashrc` or `~/.zshrc`:

```bash
export PAXSTORE_BASE_URL=https://api.whatspos.com/p-market-api
export PAXSTORE_API_KEY=your-api-key-here
export PAXSTORE_API_SECRET=your-api-secret-here
```

Then run:
```bash
source ~/.bashrc  # or source ~/.zshrc
```

**On Windows:**

1. Search for "Environment Variables" in Start Menu
2. Click "Environment Variables" button
3. Add three new User variables:
   - `PAXSTORE_BASE_URL` = `https://api.whatspos.com/p-market-api`
   - `PAXSTORE_API_KEY` = `your-api-key-here`
   - `PAXSTORE_API_SECRET` = `your-api-secret-here`

### Method 2: Command-Line (For Testing)

Pass credentials directly (replace YOUR_* with actual values):

```bash
java -jar target/paxstore-cli.jar upload \
  --base-url https://api.whatspos.com/p-market-api \
  --api-key YOUR_KEY \
  --api-secret YOUR_SECRET \
  --apk-file app.apk \
  ...
```

## Step 6: Upload Your First APK

Let's say you have an APK file called `myapp.apk` in your current directory:

```bash
java -jar target/paxstore-cli.jar upload \
  --apk-file myapp.apk \
  --app-name "My Test App" \
  --base-type N \
  --charge-type 0 \
  --models "A920" \
  --categories "WL_PS" \
  --short-desc "This is a test app" \
  --description "This is a longer description of my test app" \
  --release-notes "Version 1.0 - Initial release"
```

**Breaking it down:**
- `java -jar target/paxstore-cli.jar` - Run the tool
- `upload` - The command we want to run
- `--apk-file myapp.apk` - Path to your APK
- `--app-name "My Test App"` - Name of your app
- `--base-type N` - N means Normal app (use P for Parameter app)
- `--charge-type 0` - 0 means Free (use 1 for Paid)
- `--models "A920"` - Which PAX terminal models (comma-separated)
- `--categories "WL_PS"` - App category (from PAXStore)
- Rest are descriptions and notes

If successful, you'll see:
```
✓ Success!
Message: APK uploaded successfully

Data:
123456789
```

That number (123456789) is your APK ID - save it!

## Step 7: Submit APK for Approval

```bash
java -jar target/paxstore-cli.jar submit --apk-id 123456789
```

Replace `123456789` with the APK ID from step 6.

## Using in CI/CD (Bitbucket Pipelines)

This is where it all comes together! Add this to your `bitbucket-pipelines.yml`:

```yaml
image: maven:3.8.6-openjdk-11

pipelines:
  branches:
    main:
      - step:
          name: Deploy to PAXStore
          caches:
            - maven
          script:
            # Install PAXStore SDK (required)
            - git clone https://github.com/PAXSTORE/paxstore-openapi-java-sdk.git
            - cd paxstore-openapi-java-sdk && mvn clean install -DskipTests && cd ..
            
            # Build the CLI tool
            - git clone https://github.com/RahmanBadru/paxstore-deployment-cli.git
            - cd paxstore-deployment-cli && mvn clean package && cd ..
            
            # Upload your APK (assuming it's already built)
            - |
              java -jar paxstore-deployment-cli/target/paxstore-cli.jar upload \
                --apk-file app/build/outputs/apk/release/app-release.apk \
                --app-name "MyApp" \
                --base-type N \
                --charge-type 0 \
                --models "A920" \
                --categories "WL_PS" \
                --short-desc "Build ${BITBUCKET_BUILD_NUMBER}" \
                --description "Automated build" \
                --release-notes "Build #${BITBUCKET_BUILD_NUMBER}"
```

**Important:** In Bitbucket, go to:
- Repository Settings → Repository variables
- Add these three variables:
  - `PAXSTORE_BASE_URL`
  - `PAXSTORE_API_KEY`
  - `PAXSTORE_API_SECRET` (mark as "Secured")

## Common Problems and Solutions

### "Command not found: java"

**Problem:** Java is not installed or not in your PATH.

**Solution:**
- Install Java (see Step 1)
- Restart your terminal
- Try `java -version` again

### "Could not resolve dependencies"

**Problem:** PAXStore SDK is not installed locally.

**Solution:**
```bash
cd ~
git clone https://github.com/PAXSTORE/paxstore-openapi-java-sdk.git
cd paxstore-openapi-java-sdk
mvn clean install -DskipTests
```

### "Base URL is required"

**Problem:** Credentials are not set.

**Solution:** Set environment variables (see Step 5) or use `--base-url`, `--api-key`, `--api-secret` flags.

### "APK file not found"

**Problem:** The path to your APK is wrong.

**Solution:** 
- Check the file exists: `ls myapp.apk`
- Use full path: `--apk-file /home/user/project/myapp.apk`
- Or relative path from where you run the command

### "BUILD FAILURE" when building

**Problem:** Maven can't find dependencies.

**Solution:**
1. Make sure PAXStore SDK is installed (Step 3)
2. Check internet connection (Maven needs to download dependencies)
3. Try: `mvn clean install -U` (forces update)

## Quick Reference

### Upload APK
```bash
java -jar target/paxstore-cli.jar upload \
  --apk-file APP.apk \
  --app-name "NAME" \
  --base-type N \
  --charge-type 0 \
  --models "MODEL" \
  --categories "CATEGORY" \
  --short-desc "SHORT" \
  --description "DESCRIPTION" \
  --release-notes "NOTES"
```

### Submit for Approval
```bash
java -jar target/paxstore-cli.jar submit --apk-id ID
```

### Get App Info
```bash
java -jar target/paxstore-cli.jar get-app-info \
  --package-name com.example.app \
  --app-name "App Name"
```

### Get APK Info
```bash
java -jar target/paxstore-cli.jar get-apk-info --apk-id ID
```

### List APK Versions
```bash
java -jar target/paxstore-cli.jar get-apk-versions --app-id ID
```

## Need More Help?

1. **Check the main README:** [README.md](README.md)
2. **Open an issue:** [GitHub Issues](https://github.com/RahmanBadru/paxstore-deployment-cli/issues)
3. **PAXStore docs:** [PAXStore Developer Portal](https://developer.pax.com/)

## Summary: What You Need to Know

1. **Java** - Programming language, you need it installed
2. **Maven** - Build tool, you need it to build the CLI
3. **JAR file** - The compiled CLI tool (like an .exe file)
4. **Environment Variables** - A way to store secrets (credentials)
5. **Command-line** - Terminal/Command Prompt where you run commands

**You DO NOT need to:**
- Write any Java code
- Understand how the tool works internally
- Learn Java programming

**You DO need to:**
- Install Java and Maven once
- Run a few commands to build the tool
- Set your PAXStore credentials
- Run the tool with your APK files

That's it! You're now ready to use PAXStore CLI in your CI/CD pipeline.
