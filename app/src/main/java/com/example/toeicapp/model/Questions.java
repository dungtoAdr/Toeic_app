package com.example.toeicapp.model;

import java.io.Serializable;
import java.util.List;

public class Questions implements Serializable {
    private String audio_path;
    private String paragraph_path;
    private List<Question> questions;

    public String getParagraph_path() {
        return paragraph_path;
    }

    public void setParagraph_path(String paragraph_path) {
        this.paragraph_path = paragraph_path;
    }

    public String getAudio_path() {
        return audio_path;
    }

    public void setAudio_path(String audio_path) {
        this.audio_path = audio_path;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
