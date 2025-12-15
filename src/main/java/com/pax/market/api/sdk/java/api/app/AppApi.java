package com.pax.market.api.sdk.java.api.app;

import com.pax.market.api.sdk.java.api.app.dto.*;
import com.pax.market.api.sdk.java.base.dto.Result;

import java.util.List;

/**
 * MOCK CLASS - Replace with actual PAXStore SDK
 * This is a placeholder to allow the CLI to compile.
 * Update this when the real SDK is available.
 */
public class AppApi {
    private static final int MOCK_ERROR_CODE = 999;
    private static final String MOCK_ERROR_MESSAGE = "MOCK: SDK not yet integrated. See SDK_INTEGRATION_NOTE.md";
    
    private String baseUrl;
    private String apiKey;
    private String apiSecret;
    
    public AppApi(String baseUrl, String apiKey, String apiSecret) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }
    
    public Result<Long> uploadApk(CreateApkRequest request) {
        // TODO: Implement actual API call when SDK is available
        return new Result<>(MOCK_ERROR_CODE, MOCK_ERROR_MESSAGE, null);
    }
    
    public Result<String> submitApk(Long apkId) {
        // TODO: Implement actual API call when SDK is available
        return new Result<>(MOCK_ERROR_CODE, MOCK_ERROR_MESSAGE, null);
    }
    
    public Result<AppInfo> getAppInfoByName(String packageName, String appName) {
        // TODO: Implement actual API call when SDK is available
        return new Result<>(MOCK_ERROR_CODE, MOCK_ERROR_MESSAGE, null);
    }
    
    public Result<ApkInfo> getApkById(Long apkId) {
        // TODO: Implement actual API call when SDK is available
        return new Result<>(MOCK_ERROR_CODE, MOCK_ERROR_MESSAGE, null);
    }
    
    public Result<List<ApkVersionInfo>> getApkVersionList(Long appId) {
        // TODO: Implement actual API call when SDK is available
        return new Result<>(MOCK_ERROR_CODE, MOCK_ERROR_MESSAGE, null);
    }
    
    public Result<String> createApp(CreateSingleAppRequest request) {
        // TODO: Implement actual API call when SDK is available
        return new Result<>(MOCK_ERROR_CODE, MOCK_ERROR_MESSAGE, null);
    }
    
    public Result<Long> createApk(CreateSingleApkRequest request) {
        // TODO: Implement actual API call when SDK is available
        return new Result<>(MOCK_ERROR_CODE, MOCK_ERROR_MESSAGE, null);
    }
    
    public Result<String> deleteApk(Long apkId) {
        // TODO: Implement actual API call when SDK is available
        return new Result<>(MOCK_ERROR_CODE, MOCK_ERROR_MESSAGE, null);
    }
}
