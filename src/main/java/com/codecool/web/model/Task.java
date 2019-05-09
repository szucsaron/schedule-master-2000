package com.codecool.web.model;

public class Task {
    private int id;
    private int userId;
    private String title;
    private String content;

    public Task(int id, int userId, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return String.format("id: %d, title: %s, content: %s", id, title, content);
    }
}
