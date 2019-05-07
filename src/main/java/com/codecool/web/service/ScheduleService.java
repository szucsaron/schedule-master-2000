package com.codecool.web.service;

import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleService {

    List<Schedule> findAll() throws SQLException;

    List<Schedule> findByUser(String userId) throws SQLException;

    Schedule findById(String scheduleId, String userId) throws SQLException;

    void add(String userId, String name) throws SQLException;

    void update(String scheduleId, String userId, String name) throws SQLException;

    void delete(String scheduleId, String userId) throws SQLException;

}
