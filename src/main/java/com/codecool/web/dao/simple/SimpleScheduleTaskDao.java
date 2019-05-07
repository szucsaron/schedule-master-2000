package com.codecool.web.dao.simple;

import com.codecool.web.dao.ScheduleTaskDao;

import com.codecool.web.model.ScheduleTask;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimpleScheduleTaskDao extends AbstractDao implements ScheduleTaskDao {
    public SimpleScheduleTaskDao(Connection connection) {
        super(connection);
    }

    private ScheduleTask fetchScheduleTask(ResultSet resultSet) throws SQLException {
        int taskId = resultSet.getInt("task_id");
        int hourStart = resultSet.getInt("hour_start");
        int hourEnd = resultSet.getInt("hour_end");
        int scheduleId = resultSet.getInt("schedule_id");
        Date date = resultSet.getDate("date");
        LocalDate dateVal = date.toLocalDate();
        String title = resultSet.getString("title");
        String content = resultSet.getString("content");
        return new ScheduleTask(taskId, scheduleId, title, content, dateVal, hourStart, hourEnd);
    }

    public List<ScheduleTask> findAll(int limit) throws SQLException {
        String sql = "SELECT * FROM task RIGHT JOIN schedule_task ON task.id = task_id LIMIT ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            statement.execute();
            List<ScheduleTask> tasks = new ArrayList<>();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                tasks.add(fetchScheduleTask(rs));
            }
            return tasks;
        }
    }

    public List<ScheduleTask> findByScheduleId(int scheduleId) throws SQLException {
        String sql = "SELECT * FROM task RIGHT JOIN schedule_task ON task.id = task_id WHERE schedule_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.execute();
            List<ScheduleTask> tasks = new ArrayList<>();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                tasks.add(fetchScheduleTask(rs));
            }
            return tasks;
        }
    }

    public void addTaskToSchedule(int scheduleId, int taskId, LocalDate date, int hourStart, int hourEnd) throws SQLException {
        String sql = "INSERT INTO schedule_task(schedule_id, task_id, date, hour_start, hour_end) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.setInt(2, taskId);
            statement.setObject(3, date);
            statement.setInt(4, hourStart);
            statement.setInt(5, hourEnd);
            statement.executeUpdate();
        }
    }

    public void modifyBySchedule(int scheduleId, int taskId, LocalDate date, int hourStart, int hourEnd) throws SQLException {
        String sql = "INSERT INTO schedule_task(schedule_id, task_id, date, hour_start, hour_end) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.setInt(2, taskId);
            statement.setObject(3, date);
            statement.setInt(4, hourStart);
            statement.setInt(5, hourEnd);
            statement.executeUpdate();
        }
    }

    public void removeTaskFromSchedule(int scheduleId, int taskId) throws SQLException{
        String sql = "DELETE FROM schedule_task WHERE task_id = ? AND schedule_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            statement.setInt(2, scheduleId);
            statement.executeUpdate();
        }
    }

    public void removeTaskFromAllSchedules(int scheduleId) throws SQLException{
        String sql = "DELETE FROM schedule_task WHERE schedule_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.executeUpdate();
        }
    }
}

