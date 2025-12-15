package com.paxstore.cli.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pax.market.api.sdk.java.base.dto.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for handling API results and formatting output
 */
public class ResultHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    /**
     * Handle Result object and print output
     */
    public static <T> int handleResult(Result<T> result, String outputFormat, boolean quiet) {
        if (result == null) {
            printError("No response from API");
            return 2;
        }
        
        if ("json".equalsIgnoreCase(outputFormat)) {
            printJson(result);
            return result.getBusinessCode() == 0 ? 0 : 2;
        }
        
        if (!quiet) {
            if (result.getBusinessCode() == 0) {
                printSuccess(result);
            } else {
                printError(result);
            }
        }
        
        return result.getBusinessCode() == 0 ? 0 : 2;
    }
    
    /**
     * Print success message
     */
    private static <T> void printSuccess(Result<T> result) {
        System.out.println("✓ Success!");
        if (result.getMessage() != null && !result.getMessage().isEmpty()) {
            System.out.println("Message: " + result.getMessage());
        }
        if (result.getData() != null) {
            System.out.println("\nData:");
            System.out.println(gson.toJson(result.getData()));
        }
    }
    
    /**
     * Print error message
     */
    private static <T> void printError(Result<T> result) {
        System.err.println("✗ Error: Operation failed");
        System.err.println("Business Code: " + result.getBusinessCode());
        if (result.getMessage() != null && !result.getMessage().isEmpty()) {
            System.err.println("Message: " + result.getMessage());
        }
    }
    
    /**
     * Print error message
     */
    public static void printError(String message) {
        System.err.println("✗ Error: " + message);
    }
    
    /**
     * Print JSON output
     */
    private static <T> void printJson(Result<T> result) {
        Map<String, Object> output = new HashMap<>();
        output.put("success", result.getBusinessCode() == 0);
        output.put("businessCode", result.getBusinessCode());
        output.put("message", result.getMessage());
        output.put("data", result.getData());
        
        System.out.println(gson.toJson(output));
    }
    
    /**
     * Print validation error
     */
    public static int printValidationError(String message) {
        System.err.println("✗ Validation Error: " + message);
        return 1;
    }
    
    /**
     * Print info message
     */
    public static void printInfo(String message) {
        System.out.println("ℹ " + message);
    }
}
