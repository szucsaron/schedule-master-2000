package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.Role;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SimpleUserService implements UserService {
    private final UserDao userDao;

    public SimpleUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User addUser(String name, String password, String email, Role role, int workLoad) throws SQLException, ServiceException {
        try {
            return userDao.add(name, password, email, role, workLoad);
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public List<User> findAllExceptCurrent(int id) throws SQLException {
        return userDao.findAllExceptCurrent(id);
    }

    @Override
    public User findById(int id) throws SQLException {
        return userDao.findById(id);
    }
}
