package com.codecool.web.dao;

import com.codecool.web.model.ScheduleTask;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleTaskDao {
    List<ScheduleTask> findAll(int limit)  throws SQLException;

}
