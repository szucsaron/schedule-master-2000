

package com.codecool.web.dao.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import org.springframework.cglib.core.Local;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class    SimpleScheduleDao extends AbstractDao implements ScheduleDao {

    public SimpleScheduleDao(Connection connection) {
        super(connection);
    }

    private Schedule fetchSchedule(ResultSet resultSet) throws SQLException {
        int scheduleId = resultSet.getInt("id");
        int userId = resultSet.getInt("users_id");
        String name = resultSet.getString("name");
        return new Schedule(scheduleId,userId,name);
        /*Date startingDate = resultSet.getDate("date");
        int days = resultSet.getInt("days");
        return new Schedule(scheduleId, userId, name,startingDate,userId);*/
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
    public Schedule findById(int userId, int scheduleId) throws SQLException {
        String sql = "SELECT id, users_id, name FROM Schedule WHERE users_id = ? AND id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, scheduleId);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return fetchSchedule(resultSet);
        }
    }

    @Override
    public List<Schedule> findByUser(int userId) throws SQLException {
        String sql = "SELECT id, users_id, name FROM Schedule WHERE users_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery(sql);
            List<Schedule> schedules = new ArrayList<>();
            while (resultSet.next()) {
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules;
        }
    }

    @Override
    public Schedule add(int userId, String name) throws SQLException {
        String sql = "INSERT INTO schedule (users_id, name) VALUES(?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            statement.setString(2, name);
            statement.executeUpdate();
            int scheduleId = fetchGeneratedId(statement);
            return new Schedule(scheduleId, userId, name);
        }
    }

    @Override
    public void update(int userId, int scheduleId, String newName) throws SQLException {
        String sql = "UPDATE Schedule SET name = ?  WHERE users_id = ? AND id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.setInt(2, scheduleId);
            statement.setInt(3, scheduleId);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int userId, int scheduleId) throws SQLException {
        String sql = "DELETE FROM Schedule WHERE users_id = ? AND id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, scheduleId);
            statement.executeUpdate();
        }
    }
}
