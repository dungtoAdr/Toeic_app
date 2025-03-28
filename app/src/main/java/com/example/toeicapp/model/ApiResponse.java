package com.example.toeicapp.model;

import java.util.List;

public class ApiResponse {
    private boolean success;
    private String message;
    private List<PracticeSession> data;

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

    public List<PracticeSession> getData() {
        return data;
    }

    public void setData(List<PracticeSession> data) {
        this.data = data;
    }
}
