package com.codecool.web.dto;

import com.codecool.web.model.Task;

public class TaskDto {
    private Task task;
    private int scheduleId;
    int day;
    int hourStart;
    int hourEnd;

    public TaskDto(Task task, int scheduleId, int day, int hourStart, int hourEnd) {
        this.task = task;
        this.scheduleId = scheduleId;
        this.day = day;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
    }

    public Task getTask() {
        return task;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getDay() {
        return day;
    }

    public int getHourStart() {
        return hourStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }
}
