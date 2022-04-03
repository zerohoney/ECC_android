package com.example.myapplication;

public class MainData {

 private String content;
 private int id;
 private String title_id;

    public MainData() {
    }

    public MainData(String content, int id, String title_id) {
        this.content = content;
        this.id = id;
        this.title_id = title_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_id() {
        return title_id;
    }

    public void setTitle_id(String title_id) {
        this.title_id = title_id;
    }
}
