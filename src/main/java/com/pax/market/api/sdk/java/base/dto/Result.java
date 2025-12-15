package com.pax.market.api.sdk.java.base.dto;

/**
 * MOCK CLASS - Replace with actual PAXStore SDK
 * This is a placeholder to allow the CLI to compile.
 * Update this when the real SDK is available.
 */
public class Result<T> {
    private int businessCode;
    private String message;
    private T data;
    
    public Result() {
    }
    
    public Result(int businessCode, String message, T data) {
        this.businessCode = businessCode;
        this.message = message;
        this.data = data;
    }
    
    public int getBusinessCode() {
        return businessCode;
    }
    
    public void setBusinessCode(int businessCode) {
        this.businessCode = businessCode;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}
