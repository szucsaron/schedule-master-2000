package com.codecool.web.dao;

import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleDao {

    List<Schedule> findAll(boolean getOnlyPublic) throws SQLException;

    Schedule fetchShared(int scheduleId) throws SQLException;

    List<Schedule> findByUser(int userId) throws SQLException;

    Schedule findById(int userId, int scheduleId) throws SQLException;

    Schedule findByScheduleId( int scheduleId) throws SQLException;

    Schedule add(int userId, String name, LocalDate date, int days) throws SQLException;

    void update(int scheduleId, String name, LocalDate date, int days) throws SQLException;

    void delete(int userId, int scheduleId) throws SQLException;


}
