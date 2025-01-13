package com.example.toeicapp.model;

import java.util.List;

public class QuestionsModel {
    private boolean success;
    private String message;
    private List<Questions> result;

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

    public List<Questions> getResult() {
        return result;
    }

    public void setResult(List<Questions> result) {
        this.result = result;
    }
}
