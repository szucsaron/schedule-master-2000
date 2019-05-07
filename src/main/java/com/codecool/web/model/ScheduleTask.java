package com.codecool.web.model;

import java.time.LocalDate;
import java.util.List;

public class ScheduleTask extends Task {
    private LocalDate date;
    private int scheduleId;
    private int hourStart;
    private int hourEnd;
    public ScheduleTask(int taskid, int scheduleId, String title, String content, LocalDate date, int hourStart, int hourEnd) {
        super(taskid, title, content);
        this.scheduleId = scheduleId;
        this.date = date;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
    }

    public LocalDate getDate() {
        return date;
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
}
