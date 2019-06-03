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

    void setPublic(String scheduleId, String isPublic) throws SQLException, ServiceException;

    Schedule findById(String scheduleId, String userId) throws SQLException, ServiceException;

    Schedule findByScheduleId(String scheduleId) throws SQLException;

    void add(int userId, String name, String date, String days) throws SQLException, ServiceException;

    void update(String[] ids, String[] names, List<LocalDate> starting, List<LocalDate> finishing) throws SQLException, ServiceException;

    void delete(String scheduleIdChain, int userId) throws SQLException, ServiceException;

}
