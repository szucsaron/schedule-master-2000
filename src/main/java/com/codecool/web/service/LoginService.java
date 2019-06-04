package com.codecool.web.service;

import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface LoginService {
    public User googleLoginUser(String token) throws SQLException, ServiceException;

    public User loginUser(String email, String password) throws SQLException, ServiceException;

}
