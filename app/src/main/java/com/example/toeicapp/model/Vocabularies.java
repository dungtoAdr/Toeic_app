package com.example.toeicapp.model;

import java.util.List;

public class Vocabularies {
    private boolean success;
    private String message;
    private List<Vocabulary> data;

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

    public List<Vocabulary> getData() {
        return data;
    }

    public void setData(List<Vocabulary> data) {
        this.data = data;
    }
}
