package com.paxstore.cli.commands;

import com.pax.market.api.sdk.java.api.app.AppApi;
import com.pax.market.api.sdk.java.api.app.dto.ApkVersionInfo;
import com.pax.market.api.sdk.java.base.dto.Result;
import com.paxstore.cli.config.PaxStoreConfig;
import com.paxstore.cli.utils.ResultHandler;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Get APK version list
 */
@Command(
    name = "get-apk-versions",
    description = "List all APK versions for an app"
)
public class GetApkVersionsCommand implements Callable<Integer> {
    
    @Option(names = {"--base-url"}, description = "PAXStore API base URL")
    private String baseUrl;
    
    @Option(names = {"--api-key"}, description = "PAXStore API key")
    private String apiKey;
    
    @Option(names = {"--api-secret"}, description = "PAXStore API secret")
    private String apiSecret;
    
    @Option(names = {"--app-id"}, required = true, description = "Application ID")
    private Long appId;
    
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
        
        // Create API instance
        AppApi appApi = new AppApi(config.getBaseUrl(), config.getApiKey(), config.getApiSecret());
        
        if (!quiet) {
            ResultHandler.printInfo("Retrieving APK versions...");
        }
        
        // Get APK versions
        Result<List<ApkVersionInfo>> result = appApi.getApkVersionList(appId);
        
        return ResultHandler.handleResult(result, outputFormat, quiet);
    }
}
