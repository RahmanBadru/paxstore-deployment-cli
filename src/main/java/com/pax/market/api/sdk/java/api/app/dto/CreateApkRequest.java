package com.pax.market.api.sdk.java.api.app.dto;

import java.io.File;

/**
 * MOCK CLASS - Replace with actual PAXStore SDK
 */
public class CreateApkRequest {
    private String name;
    private File apkFile;
    private String apkType;
    private Integer chargeType;
    private String osType;
    private String type;
    private String shortDesc;
    private String description;
    private String releaseNotes;
    private File icon;
    private File featuredImage;
    private File[] screenshots;
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public File getApkFile() { return apkFile; }
    public void setApkFile(File apkFile) { this.apkFile = apkFile; }
    
    public String getApkType() { return apkType; }
    public void setApkType(String apkType) { this.apkType = apkType; }
    
    public Integer getChargeType() { return chargeType; }
    public void setChargeType(Integer chargeType) { this.chargeType = chargeType; }
    
    public String getOsType() { return osType; }
    public void setOsType(String osType) { this.osType = osType; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getShortDesc() { return shortDesc; }
    public void setShortDesc(String shortDesc) { this.shortDesc = shortDesc; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getReleaseNotes() { return releaseNotes; }
    public void setReleaseNotes(String releaseNotes) { this.releaseNotes = releaseNotes; }
    
    public File getIcon() { return icon; }
    public void setIcon(File icon) { this.icon = icon; }
    
    public File getFeaturedImage() { return featuredImage; }
    public void setFeaturedImage(File featuredImage) { this.featuredImage = featuredImage; }
    
    public File[] getScreenshots() { return screenshots; }
    public void setScreenshots(File[] screenshots) { this.screenshots = screenshots; }
}
