package com.paxstore.cli.commands;

import com.pax.market.api.sdk.java.api.app.AppApi;
import com.pax.market.api.sdk.java.api.app.dto.CreateApkRequest;
import com.pax.market.api.sdk.java.base.dto.Result;
import com.paxstore.cli.config.PaxStoreConfig;
import com.paxstore.cli.utils.FileHelper;
import com.paxstore.cli.utils.ResultHandler;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Upload APK to PAXStore
 * This is the most important command for CI/CD pipelines
 */
@Command(
    name = "upload",
    description = "Upload APK to PAXStore and create/update app"
)
public class UploadCommand implements Callable<Integer> {
    
    @Option(names = {"--base-url"}, description = "PAXStore API base URL")
    private String baseUrl;
    
    @Option(names = {"--api-key"}, description = "PAXStore API key")
    private String apiKey;
    
    @Option(names = {"--api-secret"}, description = "PAXStore API secret")
    private String apiSecret;
    
    @Option(names = {"--apk-file"}, required = true, description = "Path to APK file")
    private String apkFile;
    
    @Option(names = {"--app-name"}, required = true, description = "Application name")
    private String appName;
    
    @Option(names = {"--base-type"}, required = true, description = "Base type: N (Normal), P (Parameter)")
    private String baseType;
    
    @Option(names = {"--charge-type"}, required = true, description = "Charge type: 0 (Free), 1 (Paid)")
    private Integer chargeType;
    
    @Option(names = {"--models"}, required = true, description = "Comma-separated terminal models (e.g., A920,A930)")
    private String models;
    
    @Option(names = {"--categories"}, required = true, description = "Comma-separated categories (e.g., WL_PS,WL_SK)")
    private String categories;
    
    @Option(names = {"--short-desc"}, required = true, description = "Short description")
    private String shortDesc;
    
    @Option(names = {"--description"}, required = true, description = "Full description")
    private String description;
    
    @Option(names = {"--release-notes"}, description = "Release notes")
    private String releaseNotes;
    
    @Option(names = {"--screenshots"}, description = "Comma-separated screenshot file paths")
    private String screenshots;
    
    @Option(names = {"--icon"}, description = "Icon file path")
    private String icon;
    
    @Option(names = {"--featured-image"}, description = "Featured image file path")
    private String featuredImage;
    
    @Option(names = {"--output"}, description = "Output format: human, json", defaultValue = "human")
    private String outputFormat;
    
    @Option(names = {"--quiet", "-q"}, description = "Suppress output except errors")
    private boolean quiet;
    
    @Option(names = {"--dry-run"}, description = "Validate inputs without uploading")
    private boolean dryRun;
    
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
        
        // Validate APK file
        if (!FileHelper.validateFile(apkFile)) {
            return ResultHandler.printValidationError("APK file not found or not readable: " + apkFile);
        }
        
        if (!FileHelper.isApkFile(apkFile)) {
            return ResultHandler.printValidationError("File is not an APK: " + apkFile);
        }
        
        // Validate base type
        if (!"N".equalsIgnoreCase(baseType) && !"P".equalsIgnoreCase(baseType)) {
            return ResultHandler.printValidationError("Base type must be N (Normal) or P (Parameter)");
        }
        
        // Validate charge type
        if (chargeType != 0 && chargeType != 1) {
            return ResultHandler.printValidationError("Charge type must be 0 (Free) or 1 (Paid)");
        }
        
        // Validate icon if provided
        if (icon != null && !icon.isEmpty()) {
            if (!FileHelper.validateFile(icon)) {
                return ResultHandler.printValidationError("Icon file not found: " + icon);
            }
            if (!FileHelper.isImageFile(icon)) {
                return ResultHandler.printValidationError("Icon must be an image file (PNG, JPG, JPEG)");
            }
        }
        
        // Validate featured image if provided
        if (featuredImage != null && !featuredImage.isEmpty()) {
            if (!FileHelper.validateFile(featuredImage)) {
                return ResultHandler.printValidationError("Featured image file not found: " + featuredImage);
            }
            if (!FileHelper.isImageFile(featuredImage)) {
                return ResultHandler.printValidationError("Featured image must be an image file (PNG, JPG, JPEG)");
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
        
        if (dryRun) {
            if (!quiet) {
                ResultHandler.printInfo("Dry run: All validations passed. APK would be uploaded.");
                long fileSize = FileHelper.getFileSize(apkFile);
                ResultHandler.printInfo("APK file: " + apkFile + " (" + FileHelper.formatFileSize(fileSize) + ")");
                ResultHandler.printInfo("App name: " + appName);
                ResultHandler.printInfo("Base type: " + baseType);
                ResultHandler.printInfo("Charge type: " + chargeType);
                ResultHandler.printInfo("Models: " + models);
                ResultHandler.printInfo("Categories: " + categories);
            }
            return 0;
        }
        
        // Create API instance
        AppApi appApi = new AppApi(config.getBaseUrl(), config.getApiKey(), config.getApiSecret());
        
        // Build request
        CreateApkRequest request = new CreateApkRequest();
        request.setName(appName);
        request.setApkFile(new File(apkFile));
        request.setApkType(baseType);
        request.setChargeType(chargeType);
        
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
        
        if (releaseNotes != null && !releaseNotes.isEmpty()) {
            request.setReleaseNotes(releaseNotes);
        }
        
        if (icon != null && !icon.isEmpty()) {
            request.setIcon(new File(icon));
        }
        
        if (featuredImage != null && !featuredImage.isEmpty()) {
            request.setFeaturedImage(new File(featuredImage));
        }
        
        if (screenshots != null && !screenshots.isEmpty()) {
            String[] screenshotFiles = screenshots.split(",");
            File[] screenshotFileArray = new File[screenshotFiles.length];
            for (int i = 0; i < screenshotFiles.length; i++) {
                screenshotFileArray[i] = new File(screenshotFiles[i].trim());
            }
            request.setScreenshots(screenshotFileArray);
        }
        
        if (!quiet) {
            ResultHandler.printInfo("Uploading APK to PAXStore...");
            long fileSize = FileHelper.getFileSize(apkFile);
            ResultHandler.printInfo("APK file: " + apkFile + " (" + FileHelper.formatFileSize(fileSize) + ")");
        }
        
        // Upload APK
        Result<Long> result = appApi.uploadApk(request);
        
        return ResultHandler.handleResult(result, outputFormat, quiet);
    }
}
