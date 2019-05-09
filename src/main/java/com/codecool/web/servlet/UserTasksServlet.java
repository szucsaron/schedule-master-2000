package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.simple.SimpleScheduleDao;
import com.codecool.web.dao.simple.SimpleTaskDao;
import com.codecool.web.dto.ScheduleTaskDto;
import com.codecool.web.dto.TaskDto;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleScheduleService;
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

@WebServlet("/user_tasks")
public class UserTasksServlet extends AbstractServlet {

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
}
