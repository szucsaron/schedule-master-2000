

package com.codecool.web.dao.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;

import java.sql.*;
import java.time.LocalDate;
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
        Date date = resultSet.getDate("date");
        LocalDate localDate = date.toLocalDate();
        int days = resultSet.getInt("max_days");
        Schedule schedule = new Schedule(scheduleId, userId, name, localDate, days);
        try {
            boolean shared = resultSet.getBoolean("public");
            schedule.setPublic(shared);
        } catch (SQLException e) {}
        return schedule;
    }

    private Schedule fetchScheduleWithCreator(ResultSet resultSet) throws SQLException {
        int scheduleId = resultSet.getInt("id");
        int userId = resultSet.getInt("users_id");
        String name = resultSet.getString("name");
        Date date = resultSet.getDate("date");
        LocalDate localDate = date.toLocalDate();
        int days = resultSet.getInt("max_days");
        String creatorsName = resultSet.getString("creator");
        Schedule schedule = new Schedule(scheduleId, userId, name, localDate, days);
        try {
            boolean shared = resultSet.getBoolean("public");
            schedule.setPublic(shared);
        } catch (SQLException e) {}
        return schedule;
    }

    @Override
    public List<Schedule> findAll(boolean getOnlyPublic) throws SQLException {
        String sql;
        if (getOnlyPublic) {
            sql = "SELECT s.id, s.name, s.users_id, s.date, s.max_days, s.public, u.name AS creator " +
                    "FROM schedule AS s " +
                    "JOIN users AS u ON u.id = s.users_id " +
                    "WHERE public = true";
        } else {
            sql = "SELECT * name FROM Schedule";
        }

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Schedule> schedules = new ArrayList<>();
            while (resultSet.next()) {
                schedules.add(fetchScheduleWithCreator(resultSet));
            }
            return schedules;
        }
    }

    public Schedule fetchShared(int scheduleId) throws SQLException {
        String sql = "SELECT id, users_id, date, max_days, name FROM Schedule WHERE id = ? AND public = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.setBoolean(2, true);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return fetchSchedule(resultSet);
            } else {
                return null;
            }
        }
    }


    @Override
    public Schedule findById(int userId, int scheduleId) throws SQLException {
        String sql = "SELECT id, users_id, date, max_days, name FROM Schedule WHERE users_id = ? AND id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, scheduleId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return fetchSchedule(resultSet);
            } else {
                return null;
            }
        }
    }

    @Override
    public Schedule findByScheduleId(int scheduleId) throws SQLException {
        String sql = "SELECT id, users_id, date, max_days, name FROM Schedule WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return fetchSchedule(resultSet);
            } else {
                return null;
            }
        }
    }


    @Override
    public List<Schedule> findByUser(int userId) throws SQLException {
        String sql = "SELECT id, users_id, date, max_days, name, public FROM Schedule WHERE users_id = ? ORDER BY name";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Schedule> schedules = new ArrayList<>();
            while (resultSet.next()) {
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules;
        }
    }

    @Override
    public Schedule add(int userId, String name, LocalDate date, int days) throws SQLException {
        String sql = "INSERT INTO schedule (users_id, name, date, max_days) VALUES(?, ?, ?, ?);";
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userId);
            statement.setString(2, name);
            statement.setObject(3, date);
            statement.setInt(4, days);
            statement.executeUpdate();
            int scheduleId = fetchGeneratedId(statement);
            return new Schedule(scheduleId, userId, name, date, days);
        } finally {
            connection.setAutoCommit(autoCommit);
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

    @Override
    public void update(int scheduleId, String name, LocalDate date, int days) throws SQLException {
        String sql = "UPDATE schedule SET name=?, date=?, max_days=? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setObject(2, date);
            statement.setInt(3, days);
            statement.setInt(4, scheduleId);
            statement.executeUpdate();
        }
    }

    @Override
    public void setPublic(int scheduleId, boolean isPublic) throws SQLException {
        String sql = "UPDATE schedule SET public=? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, isPublic);
            statement.setInt(2, scheduleId);
            statement.executeUpdate();
        }
    }
}
