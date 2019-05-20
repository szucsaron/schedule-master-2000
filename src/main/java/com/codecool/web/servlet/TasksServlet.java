package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.simple.SimpleTaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleTaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/tasks")
public class TasksServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Find Schedule by userId
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskService taskService = new SimpleTaskService(new SimpleTaskDao(connection));

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int userId = loggedInUser.getId();
            List<Task> tasks = taskService.findByUserId(userId);
            sendMessage(resp, SC_OK, tasks);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection connection = getConnection(req.getServletContext())) {
            TaskService taskService = new SimpleTaskService(new SimpleTaskDao(connection));
            String taskIdChain = req.getParameter("taskIds");
            taskService.deleteByIds(taskIdChain);
        } catch (SQLException  | ServiceException ex) {
            handleSqlError(resp, ex);
        }

    }
}