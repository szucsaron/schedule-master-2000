package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {

    List<Task> findAll() throws SQLException;

    List<Task> findByScheduleId(int scheduleId) throws SQLException;

    Task findById(int id) throws SQLException;

    Task add(int id, int scheduleId, String title, String content) throws SQLException;

    void update(int id, int scheduleId, String title, String content) throws SQLException;

    void delete(String name) throws SQLException;
}
