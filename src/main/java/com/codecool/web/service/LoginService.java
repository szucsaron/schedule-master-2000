package com.codecool.web.service;

import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

public interface LoginService {
    public void loginUser(String token) throws SQLException, ServiceException;
}
