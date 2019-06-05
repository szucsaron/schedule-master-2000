package com.codecool.web.servlet;

import com.codecool.web.dao.simple.SimpleTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
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

@WebServlet("/protected/tasks")
public class TasksServlet extends AbstractServlet {
    private static final Logger logger = LoggerFactory.getLogger(TasksServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Find Schedule by userId
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskService taskService = new SimpleTaskService(new SimpleTaskDao(connection));

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int userId = loggedInUser.getId();
            List<Task> tasks = taskService.findByUserId(userId);
            logger.info("Tasks displayed");
            sendMessage(resp, SC_OK, tasks);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("error", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            User loggedInUser = (User) req.getSession().getAttribute("user");
            TaskService taskService = new SimpleTaskService(new SimpleTaskDao(connection));

            String title = req.getParameter("title");
            String content = req.getParameter("content");

            taskService.add(loggedInUser.getId(), title, content);
            logger.info("Task added");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("error", ex);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = getConnection(req.getServletContext())) {
            TaskService taskService = new SimpleTaskService(new SimpleTaskDao(connection));
            String taskIdChain = req.getParameter("taskIds");
            taskService.deleteByIds(taskIdChain);
            logger.info("Task deleted");
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
            logger.error("error", ex);
        }

    }
}