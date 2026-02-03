package com.app.dto;

public class ApiResponse<T> {
    private String code;
    private boolean success;
    private T data;
    private String message;

    public ApiResponse() {
    }

    public ApiResponse(String code, boolean success, T data, String message) {
        this.code = code;
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public ApiResponse(boolean success, T data, String message) {
        this.code = success ? "200" : "400";
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public ApiResponse(boolean success, String message) {
        this.code = success ? "200" : "400";
        this.success = success;
        this.data = null;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}