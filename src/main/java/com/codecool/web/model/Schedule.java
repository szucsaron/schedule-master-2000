package com.codecool.web.model;

import java.time.LocalDate;

public class Schedule {

    private int id;
    private int userId;
    private String name;
    private LocalDate startingDate;
    private int durationInDays;
    private String creatorsName;

    public Schedule(int id, int userId, String name, LocalDate startingDate, int durationInDays) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.startingDate = startingDate;
        this.durationInDays = durationInDays;
    }

    public Schedule(int id, int userId, String name, LocalDate startingDate, int durationInDays, String creatorsName) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.startingDate = startingDate;
        this.durationInDays = durationInDays;
        this.creatorsName = creatorsName;
    }

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

    public String getCreatorsName() {
        return creatorsName;
    }
}
