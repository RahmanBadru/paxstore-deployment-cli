package com.paxstore.cli.utils;

import java.io.File;

/**
 * Utility class for file operations
 */
public class FileHelper {
    
    /**
     * Validate that a file exists and is readable
     */
    public static boolean validateFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }
    
    /**
     * Get file extension
     */
    public static String getFileExtension(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < filePath.length() - 1) {
            return filePath.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }
    
    /**
     * Check if file is an APK
     */
    public static boolean isApkFile(String filePath) {
        return "apk".equals(getFileExtension(filePath));
    }
    
    /**
     * Check if file is an image
     */
    public static boolean isImageFile(String filePath) {
        String ext = getFileExtension(filePath);
        return "png".equals(ext) || "jpg".equals(ext) || "jpeg".equals(ext);
    }
    
    /**
     * Get file size in bytes
     */
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        return file.exists() ? file.length() : 0;
    }
    
    /**
     * Format file size for display
     */
    public static String formatFileSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }
}
