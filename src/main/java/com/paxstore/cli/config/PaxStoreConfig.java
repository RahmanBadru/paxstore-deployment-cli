package com.paxstore.cli.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration management for PAXStore CLI.
 * Supports multiple configuration sources with precedence:
 * 1. Command-line arguments (highest priority)
 * 2. Environment variables
 * 3. Configuration file (lowest priority)
 */
public class PaxStoreConfig {
    private static final String CONFIG_FILE = "paxstore-cli.properties";
    private static final String ENV_BASE_URL = "PAXSTORE_BASE_URL";
    private static final String ENV_API_KEY = "PAXSTORE_API_KEY";
    private static final String ENV_API_SECRET = "PAXSTORE_API_SECRET";
    
    private String baseUrl;
    private String apiKey;
    private String apiSecret;
    
    public PaxStoreConfig() {
        loadFromEnvironment();
        loadFromConfigFile();
    }
    
    /**
     * Load configuration from environment variables
     */
    private void loadFromEnvironment() {
        String envBaseUrl = System.getenv(ENV_BASE_URL);
        String envApiKey = System.getenv(ENV_API_KEY);
        String envApiSecret = System.getenv(ENV_API_SECRET);
        
        if (envBaseUrl != null) {
            this.baseUrl = envBaseUrl;
        }
        if (envApiKey != null) {
            this.apiKey = envApiKey;
        }
        if (envApiSecret != null) {
            this.apiSecret = envApiSecret;
        }
    }
    
    /**
     * Load configuration from properties file if it exists
     */
    private void loadFromConfigFile() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
            
            if (this.baseUrl == null) {
                this.baseUrl = props.getProperty("paxstore.baseUrl");
            }
            if (this.apiKey == null) {
                this.apiKey = props.getProperty("paxstore.apiKey");
            }
            if (this.apiSecret == null) {
                this.apiSecret = props.getProperty("paxstore.apiSecret");
            }
        } catch (IOException e) {
            // Config file is optional, ignore if not found
        }
    }
    
    public String getBaseUrl() {
        return baseUrl;
    }
    
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public String getApiKey() {
        return apiKey;
    }
    
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public String getApiSecret() {
        return apiSecret;
    }
    
    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }
    
    /**
     * Validate that all required configuration is present
     */
    public boolean isValid() {
        return baseUrl != null && !baseUrl.isEmpty() &&
               apiKey != null && !apiKey.isEmpty() &&
               apiSecret != null && !apiSecret.isEmpty();
    }
    
    /**
     * Get validation error message
     */
    public String getValidationError() {
        if (baseUrl == null || baseUrl.isEmpty()) {
            return "Base URL is required. Set PAXSTORE_BASE_URL environment variable or use --base-url";
        }
        if (apiKey == null || apiKey.isEmpty()) {
            return "API Key is required. Set PAXSTORE_API_KEY environment variable or use --api-key";
        }
        if (apiSecret == null || apiSecret.isEmpty()) {
            return "API Secret is required. Set PAXSTORE_API_SECRET environment variable or use --api-secret";
        }
        return null;
    }
}
