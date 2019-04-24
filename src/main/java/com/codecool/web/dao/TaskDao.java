package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {

    List<Task> findAll() throws SQLException;

    Task findById(int id) throws SQLException;

    Task add(String name, int percentage) throws SQLException;

    void add(int taskId, int... scheduleIds) throws SQLException;

    void updateTask(String name) throws SQLException;

    void deleteTask(String name) throws SQLException;
}
