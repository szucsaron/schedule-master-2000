package com.codecool.web.model;

import java.time.LocalDate;

public class ScheduleTask extends Task {
    private int day;
    private int scheduleId;
    private int hourStart;
    private int hourEnd;
    public ScheduleTask(int taskid, int scheduleId, String title, String content, int day, int hourStart, int hourEnd) {
        super(taskid, title, content);
        this.scheduleId = scheduleId;
        this.day = day;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
    }

    public int getDay() {
        return day;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getHourStart() {
        return hourStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    @Override
    public String toString() {
        String taskStr = super.toString();
        taskStr += String.format(", scheduleId: %d, day: %d, hourStart: %d, hourEnd %d", scheduleId, day, hourStart, hourEnd);
        return taskStr;
    }
}
