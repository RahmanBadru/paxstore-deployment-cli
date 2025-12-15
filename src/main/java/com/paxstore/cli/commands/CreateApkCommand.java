package com.paxstore.cli.commands;

import com.pax.market.api.sdk.java.api.developer.DeveloperApi;
import com.pax.market.api.sdk.java.api.base.dto.Result;
import com.pax.market.api.sdk.java.api.developer.dto.step.CreateSingleApkRequest;
import com.pax.market.api.sdk.java.api.io.UploadedFileContent;
import com.pax.market.api.sdk.java.api.util.FileUtils;
import com.paxstore.cli.config.PaxStoreConfig;
import com.paxstore.cli.utils.FileHelper;
import com.paxstore.cli.utils.ResultHandler;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Create a new APK version
 */
@Command(
    name = "create-apk",
    description = "Create a new APK version for an existing app"
)
public class CreateApkCommand implements Callable<Integer> {
    
    @Option(names = {"--base-url"}, description = "PAXStore API base URL")
    private String baseUrl;
    
    @Option(names = {"--api-key"}, description = "PAXStore API key")
    private String apiKey;
    
    @Option(names = {"--api-secret"}, description = "PAXStore API secret")
    private String apiSecret;
    
    @Option(names = {"--app-id"}, required = true, description = "Application ID")
    private Long appId;
    
    @Option(names = {"--apk-name"}, required = true, description = "APK name")
    private String apkName;
    
    @Option(names = {"--apk-file"}, required = true, description = "Path to APK file")
    private String apkFile;
    
    @Option(names = {"--apk-type"}, required = true, description = "APK type: N (Normal), P (Parameter)")
    private String apkType;
    
    @Option(names = {"--models"}, required = true, description = "Comma-separated terminal models")
    private String models;
    
    @Option(names = {"--categories"}, required = true, description = "Comma-separated categories")
    private String categories;
    
    @Option(names = {"--short-desc"}, required = true, description = "Short description")
    private String shortDesc;
    
    @Option(names = {"--description"}, required = true, description = "Full description")
    private String description;
    
    @Option(names = {"--screenshots"}, description = "Comma-separated screenshot file paths")
    private String screenshots;
    
    @Option(names = {"--icon"}, description = "Icon file path")
    private String icon;
    
    @Option(names = {"--output"}, description = "Output format: human, json", defaultValue = "human")
    private String outputFormat;
    
    @Option(names = {"--quiet", "-q"}, description = "Suppress output except errors")
    private boolean quiet;
    
    @Override
    public Integer call() {
        // Load configuration
        PaxStoreConfig config = new PaxStoreConfig();
        
        // Override with command-line arguments if provided
        if (baseUrl != null) config.setBaseUrl(baseUrl);
        if (apiKey != null) config.setApiKey(apiKey);
        if (apiSecret != null) config.setApiSecret(apiSecret);
        
        // Validate configuration
        if (!config.isValid()) {
            return ResultHandler.printValidationError(config.getValidationError());
        }
        
        // Validate app ID
        if (appId == null || appId <= 0) {
            return ResultHandler.printValidationError("App ID must be a positive number");
        }
        
        // Validate APK file
        if (!FileHelper.validateFile(apkFile)) {
            return ResultHandler.printValidationError("APK file not found or not readable: " + apkFile);
        }
        
        if (!FileHelper.isApkFile(apkFile)) {
            return ResultHandler.printValidationError("File is not an APK: " + apkFile);
        }
        
        // Validate APK type
        if (!"N".equalsIgnoreCase(apkType) && !"P".equalsIgnoreCase(apkType)) {
            return ResultHandler.printValidationError("APK type must be N (Normal) or P (Parameter)");
        }
        
        // Validate icon if provided
        if (icon != null && !icon.isEmpty()) {
            if (!FileHelper.validateFile(icon)) {
                return ResultHandler.printValidationError("Icon file not found: " + icon);
            }
            if (!FileHelper.isImageFile(icon)) {
                return ResultHandler.printValidationError("Icon must be an image file");
            }
        }
        
        // Validate screenshots if provided
        if (screenshots != null && !screenshots.isEmpty()) {
            String[] screenshotFiles = screenshots.split(",");
            for (String screenshotFile : screenshotFiles) {
                String trimmed = screenshotFile.trim();
                if (!FileHelper.validateFile(trimmed)) {
                    return ResultHandler.printValidationError("Screenshot file not found: " + trimmed);
                }
                if (!FileHelper.isImageFile(trimmed)) {
                    return ResultHandler.printValidationError("Screenshot must be an image file: " + trimmed);
                }
            }
        }
        
        // Create API instance
        DeveloperApi api = new DeveloperApi(config.getBaseUrl(), config.getApiKey(), config.getApiSecret());
        
        // Build request
        CreateSingleApkRequest request = new CreateSingleApkRequest();
        request.setAppId(appId);
        request.setApkName(apkName);
        request.setAppFile(FileUtils.readFile(apkFile));
        request.setApkType(apkType);
        
        // Parse and set models
        if (models != null && !models.isEmpty()) {
            String[] modelArray = FileHelper.processCommaSeparatedValues(models);
            request.setOsType(String.join(",", modelArray));
        }
        
        // Parse and set categories
        if (categories != null && !categories.isEmpty()) {
            String[] categoryArray = FileHelper.processCommaSeparatedValues(categories);
            request.setType(String.join(",", categoryArray));
        }
        
        request.setShortDesc(shortDesc);
        request.setDescription(description);
        
        if (icon != null && !icon.isEmpty()) {
            request.setIconFile(FileUtils.readFile(icon));
        }
        
        if (screenshots != null && !screenshots.isEmpty()) {
            String[] screenshotFiles = screenshots.split(",");
            UploadedFileContent[] screenshotFileArray = new UploadedFileContent[screenshotFiles.length];
            for (int i = 0; i < screenshotFiles.length; i++) {
                screenshotFileArray[i] = FileUtils.readFile(screenshotFiles[i].trim());
            }
            request.setScreenshotFiles(screenshotFileArray);
        }
        
        if (!quiet) {
            ResultHandler.printInfo("Creating new APK version...");
        }
        
        // Create APK
        Result<Long> result = api.createApk(request);
        
        return ResultHandler.handleResult(result, outputFormat, quiet);
    }
}
