package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.simple.SimpleUserDao;
import com.codecool.web.model.Role;
import com.codecool.web.service.UserService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends AbstractServlet{

    private static final String SQL_ERROR_CODE_UNIQUE_VIOLATION = "23505";
    private static final Logger logger = LoggerFactory.getLogger(TaskServlet.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new SimpleUserDao(connection);
            UserService userService = new SimpleUserService(userDao);

            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String repassword = req.getParameter("repassword");
            String name = req.getParameter("name");

            if(password.equals(repassword)) {
                userService.addUser(name, password, email, Role.REGULAR,9);
                logger.info("User created;"+name);
            } else {
                sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "Passwords do not match");
                logger.info("Passwords do not match");
            }

        } catch (SQLException ex) {
            if (SQL_ERROR_CODE_UNIQUE_VIOLATION.equals(ex.getSQLState())) {
                sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "User has been already registered");
                logger.info("Already registered");
            } else {
                handleSqlError(resp, ex);
                logger.error("error", ex);
            }
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
            logger.error("error", ex);
        }
    }
}
