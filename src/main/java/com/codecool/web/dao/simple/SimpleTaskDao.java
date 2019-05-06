package com.codecool.web.dao.simple;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SimpleTaskDao extends AbstractDao implements TaskDao {

    public SimpleTaskDao(Connection connection) {
        super(connection);
    }

    private Task fetchTask(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public List<Task> findAll() throws SQLException {
        return null;
    }

    @Override
    public Task findById(int id) throws SQLException {
        return null;
    }

    @Override
    public Task add(String name, int percentage) throws SQLException {
        return null;
    }

    @Override
    public void add(int taskId, int... scheduleIds) throws SQLException {

    }

    @Override
    public void updateTask(String name) throws SQLException {

    }

    @Override
    public void deleteTask(String name) throws SQLException {

    }
}
