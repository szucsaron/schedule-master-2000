package com.codecool.web.dao.simple;

import com.codecool.web.dao.AbstractDao;
import com.codecool.web.dao.TaskDao;
import org.springframework.jdbc.object.SqlCall;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SimpleTaskDao  extends AbstractDao implements TaskDao {

    public DatabaseTaskDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Task> findAll() throws SQLException {

    }

    @Override
    public Task findById(int id) throws SQLException {

    }

    @Override
    public Task add(String name, int percentage) throws SQLException {

    }

    public void add(int taskId, int... scheduleIds) throws SQLException {

    }

    public void updateTask(String name) throws SQLException {

    }

    public void deleteTask(String name) throws SQLException {

    }

    private Task fetchTask(ResultSet resultSet) throws SQLException {

    }
}
