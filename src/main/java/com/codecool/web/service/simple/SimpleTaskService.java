package com.codecool.web.service.simple;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SimpleTaskService implements TaskService {

    private TaskDao taskDao;

    public SimpleTaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<Task> findAll() throws SQLException, ServiceException {
        return taskDao.findAll();
    }

    public List<Task> findByScheduleId(String scheduleId) throws SQLException, ServiceException {
        try {
            return taskDao.findByScheduleId(Integer.parseInt(scheduleId));
        } catch (NumberFormatException e) {
            throw new ServiceException("shceduleId must be an integer");
        }
    }

    public Task findById(String id) throws SQLException, ServiceException {
        try {
            return taskDao.findById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            throw new ServiceException("id must be an integer");
        }
    }

    public int add(String title, String content) throws SQLException {
        return taskDao.add(title, content);
    }

    public void update(String id, String title, String content) throws SQLException, ServiceException {
        try {
            taskDao.update(Integer.parseInt(id), title, content);
        } catch (NumberFormatException e) {
            throw new ServiceException("id must be an integer");
        }
    }


}
