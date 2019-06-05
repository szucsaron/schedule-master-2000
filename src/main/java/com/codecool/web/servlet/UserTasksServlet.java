package com.codecool.web.servlet;

import com.codecool.web.dao.simple.SimpleTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.simple.SimpleTaskService;
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

@WebServlet("/user_tasks")
public class UserTasksServlet extends AbstractServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserTasksServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = getConnection(req.getServletContext())) {
            TaskService taskService = new SimpleTaskService(new SimpleTaskDao(connection));

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int userId = loggedInUser.getId();
            List<Task> tasks = taskService.findByUserId(userId);
            sendMessage(resp, SC_OK, tasks);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("error", ex);
        }
    }
}
