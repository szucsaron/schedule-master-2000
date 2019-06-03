package com.codecool.web.service;

import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    User addUser(String name, String password, String email, Role role) throws SQLException, ServiceException;

    List<User> findAllExceptCurrent(int id) throws SQLException;

    User findById(int id) throws SQLException;
}
