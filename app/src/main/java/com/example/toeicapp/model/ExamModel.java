package com.example.toeicapp.model;

import java.util.List;

public class ExamModel {
    private boolean success;
    private String message;
    private List<Exam> data;

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

    public List<Exam> getData() {
        return data;
    }

    public void setData(List<Exam> data) {
        this.data = data;
    }
}
