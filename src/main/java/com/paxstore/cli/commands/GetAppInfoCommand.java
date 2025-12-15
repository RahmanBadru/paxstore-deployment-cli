package com.paxstore.cli.commands;

import com.pax.market.api.sdk.java.api.app.AppApi;
import com.pax.market.api.sdk.java.api.app.dto.AppInfo;
import com.pax.market.api.sdk.java.base.dto.Result;
import com.paxstore.cli.config.PaxStoreConfig;
import com.paxstore.cli.utils.ResultHandler;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

/**
 * Get app information
 */
@Command(
    name = "get-app-info",
    description = "Get application information"
)
public class GetAppInfoCommand implements Callable<Integer> {
    
    @Option(names = {"--base-url"}, description = "PAXStore API base URL")
    private String baseUrl;
    
    @Option(names = {"--api-key"}, description = "PAXStore API key")
    private String apiKey;
    
    @Option(names = {"--api-secret"}, description = "PAXStore API secret")
    private String apiSecret;
    
    @Option(names = {"--package-name"}, required = true, description = "Package name (e.g., com.example.app)")
    private String packageName;
    
    @Option(names = {"--app-name"}, required = true, description = "Application name")
    private String appName;
    
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
        
        // Validate inputs
        if (packageName == null || packageName.isEmpty()) {
            return ResultHandler.printValidationError("Package name is required");
        }
        
        if (appName == null || appName.isEmpty()) {
            return ResultHandler.printValidationError("App name is required");
        }
        
        // Create API instance
        AppApi appApi = new AppApi(config.getBaseUrl(), config.getApiKey(), config.getApiSecret());
        
        if (!quiet) {
            ResultHandler.printInfo("Retrieving app information...");
        }
        
        // Get app info
        Result<AppInfo> result = appApi.getAppInfoByName(packageName, appName);
        
        return ResultHandler.handleResult(result, outputFormat, quiet);
    }
}
