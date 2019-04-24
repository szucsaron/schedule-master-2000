package com.codecool.web.dao;

import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao{

    List<User> findAll() throws SQLException;

    User findById(int id) throws SQLException;

    User add(String name, int percentage) throws SQLException;

    void add(int userId, int... scheduleIds) throws SQLException;

    void updateUser(String name) throws SQLException;

    void deleteUser(String name) throws SQLException;
}
