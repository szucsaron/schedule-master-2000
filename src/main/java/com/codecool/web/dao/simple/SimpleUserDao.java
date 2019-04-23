package com.codecool.web.dao.simple;

import com.codecool.web.dao.AbstractDao;
import com.codecool.web.dao.UserDao;

import java.sql.Connection;

public class SimpleUserDao extends AbstractDao implements UserDao  {
    public SimpleUserDao(Connection connection) {
        super(connection);
    }
}
