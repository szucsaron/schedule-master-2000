package com.codecool.web.dao.simple;

import com.codecool.web.dao.AbstractDao;
import com.codecool.web.dao.TaskDao;

import java.sql.Connection;

public class SimpleTaskDao  extends AbstractDao implements TaskDao {
    public SimpleTaskDao(Connection connection) {
        super(connection);
    }
}
