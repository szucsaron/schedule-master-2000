package com.codecool.web.service.simple;


import com.codecool.web.dao.ScheduleDao;

public class SimpleScheduleService {

    private final ScheduleDao scheduleDao;

    public SimpleScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }


}
