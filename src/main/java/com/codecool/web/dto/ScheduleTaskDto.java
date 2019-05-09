package com.codecool.web.dto;

import com.codecool.web.model.Schedule;

import java.util.List;

public class ScheduleTaskDto {

    private Schedule schedule;
    private List<TaskDto> taskDto;

    public ScheduleTaskDto(Schedule schedule, List<TaskDto> taskDto) {
        this.schedule = schedule;
        this.taskDto = taskDto;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<TaskDto> getTaskDto() {
        return taskDto;
    }
}
