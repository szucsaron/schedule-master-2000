package com.codecool.web.service;

import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface ScheduleTaskService {
    void addToSchedule(String scheduleId, String taskId, String date, String hourStart, String hourEnd) throws SQLException, ServiceException;
}
