package com.codecool.web.dao.simple;


import com.codecool.web.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleTaskDao extends AbstractDao {
    public SimpleTaskDao(Connection connection) {
        super(connection);
    }

    private Task fetchTask(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Task task = new Task(id, name);
        return task;
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

    public List<Task> findByScheduleId(int scheduleId) throws SQLException{
        String sql = "SELECT * FROM task LEFT JOIN schedule_task ON task.id = task_id WHERE schedule_id = ?";
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

    public Task findById(int taskId) throws SQLException {
        String sql = "SELECT * FROM task WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            if (rs.next()) {

                return fetchTask(rs);
            } else {
                return null;
            }
        }
    }

    public void add(int id, int scheduleId, String title, String content) throws SQLException {
        String sql = "";
    }

}
