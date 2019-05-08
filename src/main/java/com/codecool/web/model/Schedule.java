package com.codecool.web.model;

import java.time.LocalDate;

public class Schedule {

    private int id;
    private int userId;
    private String name;
    private LocalDate startingDate;
    private int durationInDays;


    // TODO : Remove old constructor (testing only)
    public Schedule(int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;

    }

    public Schedule(int id, int userId, String name, LocalDate startingDate, int durationInDays) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.startingDate = startingDate;
        this.durationInDays = durationInDays;
    }

    /*public void addTast(Task task) {
    }

    public void getTasks() {
    }*/

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public int getDurationInDays() {
        return durationInDays;
    }
}
