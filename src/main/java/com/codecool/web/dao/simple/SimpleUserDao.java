package com.codecool.web.dao.simple;

import com.codecool.web.dao.AbstractDao;
import com.codecool.web.dao.UserDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SimpleUserDao extends AbstractDao implements UserDao  {
    public SimpleUserDao(Connection connection) {
        super(connection);
    }

    public DatabaseUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<User> findAll() throws SQLException {

    }

    @Override
    public User findById(int id) throws SQLException {

    }

    @Override
    public User add(String name, int percentage) throws SQLException {

    }

    public void add(int userId, int... scheduleIds) throws SQLException {

    }

    public void updateUser(String name) throws SQLException {

    }

    public void deleteUser(String name) throws SQLException {

    }

    private User fetchUser(ResultSet resultSet) throws SQLException {

    }
}
