package com.codecool.web.service;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TaskService {

    private TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
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

    public void addToSchedule(String scheduleId, String taskId, String date, String hourStart, String hourEnd) throws SQLException, ServiceException {
        int scheduleIdVal;
        int taskIdVal;
        int hourStartVal;
        int hourEndVal;
        LocalDate dateVal;
        try {
            scheduleIdVal = Integer.parseInt(scheduleId);
        } catch (NumberFormatException e) {
            throw new ServiceException("scheduleId must be an integer");
        }
        try {
            taskIdVal = Integer.parseInt(taskId);
        } catch (NumberFormatException e) {
            throw new ServiceException("taskIdVal must be an integer");
        }
        try {
            hourStartVal = Integer.parseInt(hourStart);
        } catch (NumberFormatException e) {
            throw new ServiceException("hourStartVal must be an integer");
        }
        try {
            hourEndVal = Integer.parseInt(hourEnd);
        } catch (NumberFormatException e) {
            throw new ServiceException("hourEndVal must be an integer");
        }
        try {
            dateVal = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new ServiceException("Date must follow the correct format: yyyy-mm-dd");
        }
        taskDao.addToSchedule(scheduleIdVal, taskIdVal, dateVal, hourStartVal, hourEndVal);
    }
}
