package com.codecool.web.dao.simple;


import com.codecool.web.dao.TaskDao;
import com.codecool.web.dto.TaskDto;
import com.codecool.web.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleTaskDao extends AbstractDao implements TaskDao {
    public SimpleTaskDao(Connection connection) {
        super(connection);
    }

    public List<Task> findAll() throws SQLException {
        String sql = "SELECT * FROM task";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            List<Task> tasks = new ArrayList<>();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {

                tasks.add(fetchTask(rs));
            }
            return tasks;
        }
    }

    public Task findById(int id) throws SQLException {
        String sql = "SELECT * FROM task WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            if (rs.next()) {
                return fetchTask(rs);
            } else {
                return null;
            }
        }
    }

    public List<TaskDto> findDtosByScheduleId(int scheduleId) throws SQLException {
        String sql = "SELECT task.*, schedule_task.schedule_id, day, hour_start, hour_end FROM task " +
                "JOIN schedule_task ON task.id = task_id " +
                "WHERE schedule_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.execute();
            List<TaskDto> tasks = new ArrayList<>();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                tasks.add(fetchDto(rs));
            }
            return tasks;
        }
    }

    public List<Task> findUnscheduledDtosByScheduleId(int scheduleId) throws SQLException {
        String sql = "SELECT task.*, schedule_task.schedule_id, day, hour_start, hour_end FROM task " +
                "JOIN schedule_task ON task.id = task_id " +
                "WHERE schedule_id != ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.execute();
            List<Task> tasks = new ArrayList<>();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                tasks.add(fetchTask(rs));
            }
            return tasks;
        }
    }

    public TaskDto findDtoById(int scheduleId, int taskId) throws SQLException {
        String sql = "SELECT task.*, schedule_task.schedule_id, day, hour_start, hour_end FROM task " +
                "JOIN schedule_task ON task.id = task_id " +
                "WHERE schedule_id = ? AND task_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.setInt(2, taskId);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            if (rs.next()) {
                return fetchDto(rs);
            } else {
                return null;
            }
        }
    }

    public List<String> findDtoByTaskId(int taskId) throws SQLException {
        List<String> schedules = new ArrayList<>();
        String sql = "SELECT schedule.name FROM schedule " +
                "JOIN schedule_task ON schedule.id = schedule_id " +
                "WHERE task_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                schedules.add(rs.getString("name"));
            }
        }
        return schedules;
    }

    public int add(String title, String content) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        String sql = "INSERT INTO task (title, content) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, title);
            statement.setString(2, content);
            statement.executeUpdate();
            return fetchGeneratedId(statement);
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    public void update(int id, String title, String content) throws SQLException {
        String sql = "UPDATE task SET title = ?, content = ? WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, content);
            statement.setInt(3, id);
            statement.executeUpdate();
        }
    }

    public void updateLink(int scheduleId, int taskId, int day, int hourStart, int hourEnd) throws SQLException {
        String sql = "UPDATE schedule_task SET day = ?, hour_start = ?, hour_end = ? WHERE schedule_id = ? AND task_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, day);
            statement.setInt(2, hourStart);
            statement.setObject(3, hourEnd);
            statement.setInt(4, scheduleId);
            statement.setInt(5, taskId);
            statement.executeUpdate();
        }
    }

    public void attachTaskToSchedule(int scheduleId, int taskId, int day, int hourStart, int hourEnd) throws
            SQLException {
        String sql = "INSERT INTO schedule_task(schedule_id, task_id, day, hour_start, hour_end) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.setInt(2, taskId);
            statement.setObject(3, day);
            statement.setInt(4, hourStart);
            statement.setInt(5, hourEnd);
            statement.executeUpdate();
        }
    }


    private Task fetchTask(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String content = resultSet.getString("content");

        return new Task(id, title, content);
    }

    private TaskDto fetchDto(ResultSet resultSet) throws SQLException {
        Task task = fetchTask(resultSet);
        int scheduleId = resultSet.getInt("schedule_id");
        int day = resultSet.getInt("day");
        int hourStart = resultSet.getInt("hour_start");
        int hourEnd = resultSet.getInt("hour_end");
        return new TaskDto(task, scheduleId, day, hourStart, hourEnd);
    }
}
