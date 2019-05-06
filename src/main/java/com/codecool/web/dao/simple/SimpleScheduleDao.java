package com.codecool.web.dao.simple;

import com.codecool.web.dao.AbstractDao;
import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SimpleScheduleDao extends AbstractDao implements ScheduleDao {

    public SimpleScheduleDao(Connection connection) {
        super(connection);
    }

    private Schedule fetchSchedule(ResultSet resultSet) throws SQLException {
        int scheduleId = resultSet.getInt("id");
        int userId = resultSet.getInt("users_id");
        String name = resultSet.getString("name");
        return new Schedule(scheduleId, userId, name);
    }
    @Override
    public List<Schedule> findAll() throws SQLException {
        String sql = "SELECT id, users_id, name FROM Schedule";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Schedule> schedules = new ArrayList<>();
            while (resultSet.next()) {
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules;
        }
    }

    @Override
    public Schedule findById(int id) throws SQLException {
        return null;
    }

    @Override
    public Schedule add(int userId, String name) throws SQLException {
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
