package com.codecool.web.service.simple;


import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.service.ScheduleService;

import java.sql.SQLException;
import java.util.List;

public class SimpleScheduleService implements ScheduleService {

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
    public Schedule findById(String scheduleId, String userId) throws SQLException {
        return scheduleDao.findById(Integer.parseInt(scheduleId), Integer.parseInt(userId));
    }

    @Override
    public void add(String userId, String name) throws SQLException {
        scheduleDao.add(Integer.parseInt(userId), name);
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
