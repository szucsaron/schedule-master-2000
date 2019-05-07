package com.codecool.web.dao;

import com.codecool.web.model.ScheduleTask;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleTaskDao {
    List<ScheduleTask> findAll(int limit) throws SQLException;

    List<ScheduleTask> findByScheduleId(int scheduleId) throws SQLException;

    void addTaskToSchedule(int scheduleId, int taskId, LocalDate date, int hourStart, int hourEnd) throws SQLException;

    void removeTaskFromSchedule(int scheduleId, int taskId) throws SQLException;

    void removeTaskFromAllSchedules(int scheduleId) throws SQLException;

}
