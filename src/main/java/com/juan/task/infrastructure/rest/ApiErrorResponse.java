package com.juan.task.infrastructure.rest;

public class ApiErrorResponse {

    private int status;
    private String message;
    
    protected ApiErrorResponse() {
    	
    }

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}