package com.codecool.web.dao.simple;

import com.codecool.web.dao.AbstractDao;
import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SimpleScheduleDao extends AbstractDao implements ScheduleDao {

    public SimpleScheduleDao(Connection connection) {
        super(connection);
    }

    private Schedule fetchSchedule(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public List<Schedule> findAll() throws SQLException {
        return null;
    }

    @Override
    public Schedule findById(int id) throws SQLException {
        return null;
    }

    @Override
    public Schedule add(String name, int percentage) throws SQLException {
        return null;
    }

    @Override
    public void add(int scheduleId, int... scheduleIds) throws SQLException {

    }

    @Override
    public void updateSchedule(String name) throws SQLException {

    }

    @Override
    public void deleteSchedule(String name) throws SQLException {

    }
}
