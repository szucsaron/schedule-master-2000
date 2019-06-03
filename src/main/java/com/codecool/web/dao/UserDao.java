package com.codecool.web.dao;

import com.codecool.web.model.Role;
import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao{

    List<User> findAllExceptCurrent(int id) throws SQLException;

    User findById(int id) throws SQLException;

    User add(String name, int percentage) throws SQLException;

    User add(String name, String password, String email, Role role) throws SQLException;

    void updateUser(String name) throws SQLException;

    void deleteUser(String name) throws SQLException;

     User findByEmail(String email) throws SQLException;
}
