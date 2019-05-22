package com.codecool.web.service;

import com.codecool.web.model.Schedule;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    List<Schedule> findAll(boolean getOnlyPublic) throws SQLException, ServiceException;

    List<Schedule> findByUser(String userId) throws SQLException, ServiceException;

    Schedule fetchShared(String scheduleId) throws SQLException, ServiceException;

    Schedule findById(String scheduleId, String userId) throws SQLException, ServiceException;

    void add(int userId, String name, String date, String days) throws SQLException, ServiceException;

    void update(String scheduleId, String userId, String name) throws SQLException, ServiceException;

    void delete(String scheduleIdChain, int userId) throws SQLException, ServiceException;

}
