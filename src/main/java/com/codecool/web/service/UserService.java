package com.codecool.web.service;

import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface UserService {

    User addUser(String name, String password, String email, Role role) throws SQLException, ServiceException;

}
