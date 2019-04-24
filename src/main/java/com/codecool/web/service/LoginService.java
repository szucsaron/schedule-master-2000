package com.codecool.web.service;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;

import java.sql.SQLException;

public class LoginService {
    package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.ServiceException;
import javafx.concurrent.Service;

import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;

    public final class LoginService {

        private final UserDao userDao;

        public SimpleLoginService(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        public User loginUser(String email, String password) throws SQLException, ServiceException {
            try {
                User user = userDao.findByEmail(email);
                if (user == null || !user.getPassword().equals(password)) {
                    throw new ServiceException("Bad login");
                }
                return user;
            } catch (IllegalArgumentException ex) {
                throw new ServiceException(ex.getMessage());
            }
        }
    }
}
