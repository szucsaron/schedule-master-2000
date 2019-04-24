package com.codecool.web.dao.simple;

import com.codecool.web.dao.AbstractDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SimpleUserDao extends AbstractDao implements UserDao {

    public SimpleUserDao(Connection connection) {
        super(connection);
    }

    private User fetchUser(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {
        return null;
    }

    @Override
    public User findById(int id) throws SQLException {
        return null;
    }

    @Override
    public User add(String name, int percentage) throws SQLException {
        return null;
    }

    @Override
    public void add(int userId, int... scheduleIds) throws SQLException {

    }

    @Override
    public void updateUser(String name) throws SQLException {

    }

    @Override
    public void deleteUser(String name) throws SQLException {

    }
}
