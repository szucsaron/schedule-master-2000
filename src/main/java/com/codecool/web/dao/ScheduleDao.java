package com.codecool.web.dao;

import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDao {

    List<Schedule> findAll() throws SQLException;

    Schedule findById(int id) throws SQLException;

    Schedule add(String name, int percentage) throws SQLException;

    void add(int scheduleId, int... scheduleIds) throws SQLException;

    void updateSchedule(String name) throws SQLException;

    void deleteSchedule(String name) throws SQLException;
}
