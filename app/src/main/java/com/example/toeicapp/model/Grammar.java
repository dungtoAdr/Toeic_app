package com.example.toeicapp.model;

public class Grammar {
    private int id;
    private String name;
    private String url;
    private boolean isListenning;

    public boolean isListenning() {
        return isListenning;
    }
    public void setListenning(boolean listenning) {
        isListenning = listenning;
    }

    public Grammar(int id, String name, boolean isListenning) {
        this.id = id;
        this.name = name;
        this.isListenning = isListenning;
    }

    public Grammar(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Grammar(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
