package com.codecool.web.model;

import java.time.LocalDate;

public class Schedule {

    private int id;
    private int userId;
    private String name;
    private LocalDate startingDate;
    private int durationInDays;
    private String creatorsName;
    private boolean isPublic;

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

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
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

    public boolean isPublic() {
        return isPublic;
    }
}
