package com.codecool.web.service.simple;


import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SimpleScheduleService extends AbstractService implements ScheduleService  {

    private final ScheduleDao scheduleDao;

    public SimpleScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }


    @Override
    public List<Schedule> findAll() throws SQLException {
        return scheduleDao.findAll();
    }

    @Override
    public List<Schedule> findByUser(String userId) throws SQLException {
        return scheduleDao.findByUser(Integer.parseInt(userId));
    }

    @Override
    public Schedule findById(String userId, String scheduleId) throws SQLException {
        return scheduleDao.findById(Integer.parseInt(userId), Integer.parseInt(scheduleId));
    }

    @Override
    public void add(int userId, String name, String date, String days) throws SQLException, ServiceException {
        int daysVal = fetchInt(days, "days");
        LocalDate dateVal = fetchDate(date, "date");
        scheduleDao.add(userId, name, dateVal, daysVal);
    }

    @Override
    public void update(String scheduleId, String userId, String name) throws SQLException {
        scheduleDao.update(Integer.parseInt(userId), Integer.parseInt(scheduleId), name);
    }

    @Override
    public void delete(String scheduleId, String userId) throws SQLException {
        scheduleDao.delete(Integer.parseInt(scheduleId), Integer.parseInt(userId));

    }
}
