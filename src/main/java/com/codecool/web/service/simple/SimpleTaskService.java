package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleTaskDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.ScheduleTask;
import com.codecool.web.model.Task;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SimpleTaskService extends AbstractService implements TaskService {

    private TaskDao taskDao;
    private ScheduleTaskDao scheduleTaskDao;

    public SimpleTaskService(TaskDao taskDao, ScheduleTaskDao scheduleTaskDao) {
        this.taskDao = taskDao;
        this.scheduleTaskDao = scheduleTaskDao;
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

    public void addNewToSchedule(String scheduleId, String title, String content, String date, String hourStart, String hourEnd) throws ServiceException, SQLException{
        int scheduleIdVal = fetchInt(scheduleId, "scheduleId");
        int hourStartVal = fetchInt(hourStart, "hourStart");
        int hourSEndVal = fetchInt(hourEnd, "hourEnd");
        LocalDate dateVal = fetchDate(date, "date");
        int taskIdVal = taskDao.add(title, content);
        scheduleTaskDao.addTaskToSchedule(scheduleIdVal, taskIdVal, dateVal, hourStartVal, hourSEndVal);

    }

    public void addExistingToSchedule(String scheduleId, String taskId, String date, String hourStart, String hourEnd) throws ServiceException, SQLException {
        int scheduleIdVal = fetchInt(scheduleId, "scheduleId");
        int taskIdVal = fetchInt(taskId, "taskId");
        int hourStartVal = fetchInt(hourStart, "hourStart");
        int hourSEndVal = fetchInt(hourEnd, "hourEnd");
        LocalDate dateVal = fetchDate(date, "date");
        scheduleTaskDao.addTaskToSchedule(scheduleIdVal, taskIdVal, dateVal, hourStartVal, hourSEndVal);
    }
}
