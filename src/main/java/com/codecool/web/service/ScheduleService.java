package com.codecool.web.service;

import java.sql.SQLEtaskception;
import java.util.List;

public class ScheduleService {

    public List<Schedule> getSchedules() throws SQLEtaskception {
        return scheduleDao.findAll();
    }

    @Override
    public Schedule getSchedule(String id) throws SQLEtaskception, ServiceEtaskception {

    }

    @Override
    public Schedule addSchedule(String name, String percentage) throws SQLEtaskception, ServiceEtaskception {

    }

    public void updateSchedule(String name) throws SQLEtaskception, ServiceEtaskception {

    }

    public void deleteSchedule(String name) throws SQLEtaskception, ServiceEtaskception {

    }

    @Override
    public void addScheduleToTasks(String scheduleId, String... taskIds) throws SQLEtaskception, ServiceEtaskception {

    }

    @Override
    public List<Task> getScheduleTasks(String scheduleId) throws SQLEtaskception, ServiceEtaskception {

    }

    private int[] parseTaskIds(String[] taskIds) throws ServiceEtaskception {

    }


}
}
