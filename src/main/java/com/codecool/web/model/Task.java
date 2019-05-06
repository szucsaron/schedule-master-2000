package com.codecool.web.model;

public class Task {
    private int id;
    private String title;
    private String content;

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return "Id: " + id + ", " + "title: " + title;
    }

}
