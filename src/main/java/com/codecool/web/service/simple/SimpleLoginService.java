package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;

//import javafx.concurrent.Service;

public final class SimpleLoginService implements LoginService {

    private final UserDao userDao;

    public SimpleLoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User loginUser(String email, String password) throws SQLException, ServiceException {
        try {
            User user = userDao.findByEmailPassword(email, password);
            if (user == null) {
                throw new ServiceException("Bad login: email not registered!");
            }
            return user;
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}

