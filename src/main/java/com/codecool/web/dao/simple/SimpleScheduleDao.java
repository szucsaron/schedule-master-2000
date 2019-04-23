package com.codecool.web.dao.simple;

import com.codecool.web.dao.AbstractDao;
import com.codecool.web.dao.ScheduleDao;

import java.sql.Connection;

public class SimpleScheduleDao  extends AbstractDao implements ScheduleDao {
    public SimpleScheduleDao(Connection connection) {
        super(connection);
    }
}
