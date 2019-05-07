package com.codecool.web.service;

import com.codecool.web.model.Task;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface TaskService {
    List<Task> findAll() throws SQLException, ServiceException;

    List<Task> findByScheduleId(String scheduleId) throws SQLException, ServiceException;

    Task findById(String id) throws SQLException, ServiceException;

    int add(String title, String content) throws SQLException;

    void update(String id, String title, String content) throws SQLException, ServiceException;

}
