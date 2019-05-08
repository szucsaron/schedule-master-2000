package com.codecool.web.service;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dto.TaskDto;
import com.codecool.web.model.ScheduleTask;
import com.codecool.web.model.Task;
import com.codecool.web.service.exception.ServiceException;
import org.springframework.jdbc.support.xml.SqlXmlFeatureNotImplementedException;

import java.sql.SQLException;
import java.util.List;

public interface TaskService {
    List<TaskDto> findDtosByScheduleId(String scheduleId) throws SQLException, ServiceException;

    TaskDto findDtoById(String scheduleId, String id) throws SQLException, ServiceException;

    int add(String title, String content) throws SQLException;

    void attachTaskToSchedule(String scheduleId, String taskId, String day, String hourStart, String hourEnd)
            throws ServiceException, ServiceException;

    void update(String id, String title, String content) throws SQLException, ServiceException;

    void updateLink(String id, String scheduleId, String day, String hourStart, String hourEnd) throws SQLException, ServiceException;
}
