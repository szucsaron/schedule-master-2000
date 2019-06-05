package com.codecool.web.servlet;

import com.codecool.web.dao.simple.SimpleUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.UserService;
import com.codecool.web.service.simple.SimpleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/users")
public class UsersServlet extends AbstractServlet {
    private static final Logger logger = LoggerFactory.getLogger(TasksServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserService userService = new SimpleUserService(new SimpleUserDao(connection));

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int userId = loggedInUser.getId();
            List<User> users = userService.findAllExceptCurrent(userId);
            sendMessage(resp, SC_OK, users);
            logger.info("Users displayed");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
