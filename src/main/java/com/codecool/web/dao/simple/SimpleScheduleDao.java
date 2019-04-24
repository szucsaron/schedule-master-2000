package com.codecool.web.dao.simple;

import com.codecool.web.dao.AbstractDao;
import com.codecool.web.dao.ScheduleDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SimpleScheduleDao  extends AbstractDao implements ScheduleDao {
    public SimpleScheduleDao(Connection connection) {
        super(connection);
    }

    public DatabaseScheduleDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Schedule> findAll() throws SQLException {

    }

    @Override
    public Schedule findById(int id) throws SQLException {

    }

    @Override
    public Schedule add(String name, int percentage) throws SQLException {

    }

    public void add(int scheduleId, int... scheduleIds) throws SQLException {

    }

    public void updateSchedule(String name) throws SQLException {

    }

    public void deleteSchedule(String name) throws SQLException {

    }

    private Schedule fetchSchedule(ResultSet resultSet) throws SQLException {

    }
}
