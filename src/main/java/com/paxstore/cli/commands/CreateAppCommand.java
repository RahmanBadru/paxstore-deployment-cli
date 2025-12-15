package com.paxstore.cli.commands;

import com.pax.market.api.sdk.java.api.developer.DeveloperApi;
import com.pax.market.api.sdk.java.api.base.dto.Result;
import com.pax.market.api.sdk.java.api.developer.dto.step.CreateSingleAppRequest;
import com.paxstore.cli.config.PaxStoreConfig;
import com.paxstore.cli.utils.ResultHandler;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

/**
 * Create a new app
 */
@Command(
    name = "create-app",
    description = "Create a new application"
)
public class CreateAppCommand implements Callable<Integer> {
    
    @Option(names = {"--base-url"}, description = "PAXStore API base URL")
    private String baseUrl;
    
    @Option(names = {"--api-key"}, description = "PAXStore API key")
    private String apiKey;
    
    @Option(names = {"--api-secret"}, description = "PAXStore API secret")
    private String apiSecret;
    
    @Option(names = {"--app-name"}, required = true, description = "Application name")
    private String appName;
    
    @Option(names = {"--app-key"}, required = true, description = "Application key")
    private String appKey;
    
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
        if (appName == null || appName.isEmpty()) {
            return ResultHandler.printValidationError("App name is required");
        }
        
        if (appKey == null || appKey.isEmpty()) {
            return ResultHandler.printValidationError("App key is required");
        }
        
        // Create API instance
        DeveloperApi api = new DeveloperApi(config.getBaseUrl(), config.getApiKey(), config.getApiSecret());
        
        // Build request
        CreateSingleAppRequest request = new CreateSingleAppRequest();
        request.setAppName(appName);
        request.setAppKey(appKey);
        
        if (!quiet) {
            ResultHandler.printInfo("Creating new app: " + appName);
        }
        
        // Create app
        Result<String> result = api.createApp(request);
        
        return ResultHandler.handleResult(result, outputFormat, quiet);
    }
}
