package com.pax.market.api.sdk.java.api.app.dto;

import java.io.File;

/**
 * MOCK CLASS - Replace with actual PAXStore SDK
 */
public class CreateSingleApkRequest {
    private Long appId;
    private String apkName;
    private File apkFile;
    private String apkType;
    private String osType;
    private String type;
    private String shortDesc;
    private String description;
    private File icon;
    private File[] screenshots;
    
    // Getters and setters
    public Long getAppId() { return appId; }
    public void setAppId(Long appId) { this.appId = appId; }
    
    public String getApkName() { return apkName; }
    public void setApkName(String apkName) { this.apkName = apkName; }
    
    public File getApkFile() { return apkFile; }
    public void setApkFile(File apkFile) { this.apkFile = apkFile; }
    
    public String getApkType() { return apkType; }
    public void setApkType(String apkType) { this.apkType = apkType; }
    
    public String getOsType() { return osType; }
    public void setOsType(String osType) { this.osType = osType; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getShortDesc() { return shortDesc; }
    public void setShortDesc(String shortDesc) { this.shortDesc = shortDesc; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public File getIcon() { return icon; }
    public void setIcon(File icon) { this.icon = icon; }
    
    public File[] getScreenshots() { return screenshots; }
    public void setScreenshots(File[] screenshots) { this.screenshots = screenshots; }
}
