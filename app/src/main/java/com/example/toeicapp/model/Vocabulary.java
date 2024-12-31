package com.example.toeicapp.model;

public class Vocabulary {
    private String word;
    private String pronunciation;
    private String meaning;
    private String audio_path;

    // Getters and setters
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getAudioPath() {
        return audio_path;
    }

    public void setAudioPath(String audio_path) {
        this.audio_path = audio_path;
    }
}

