package com.codecool.web.dto;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;

import java.util.List;

public class UserSchedulesDto {
    private User user;
    private List<Schedule> schedules;

    public UserSchedulesDto(User user, List<Schedule> schedules) {
        this.user = user;
        this.schedules = schedules;
    }

    public User getUser() {
        return user;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }
}
