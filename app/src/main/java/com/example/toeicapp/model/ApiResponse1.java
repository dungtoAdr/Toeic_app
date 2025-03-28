package com.example.toeicapp.model;

import java.util.List;

public class ApiResponse1 {
    private boolean success;
    private String message;
    private PracticeSession1 data;
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PracticeSession1 getData() {
        return data;
    }

    public void setData(PracticeSession1 data) {
        this.data = data;
    }
}
