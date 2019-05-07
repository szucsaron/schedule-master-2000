package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleTaskDao;
import com.codecool.web.service.ScheduleTaskService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class SimpleScheduleTaskService implements ScheduleTaskService {
    private ScheduleTaskDao scheduleTaskDao;

    public SimpleScheduleTaskService(ScheduleTaskDao scheduleTaskDao) {
        this.scheduleTaskDao = scheduleTaskDao;
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
        scheduleTaskDao.addTaskToSchedule(scheduleIdVal, taskIdVal, dateVal, hourStartVal, hourEndVal);
    }
}
