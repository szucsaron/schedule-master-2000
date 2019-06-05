package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.simple.SimpleTaskDao;
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

@WebServlet("/scheduleTable")
public class ScheduleTableServlet extends AbstractServlet{
    private static final Logger logger = LoggerFactory.getLogger(TaskServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Find Schedule by userId
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new SimpleTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);
            String scheduleId = req.getParameter("scheduleId");
            String taskId = req.getParameter("taskId");
            String day = req.getParameter("day");
            String hourStart = req.getParameter("hourStart");
            String hourEnd = req.getParameter("hourEnd");

            taskService.detachTaskFromSchedule(scheduleId, taskId);
            taskService.attachTaskToSchedule(scheduleId, taskId, day, hourStart, hourEnd);

            logger.info("Schedule table created");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("error", ex);
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.error("error", e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Find Schedule by userId
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new SimpleTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);
            String scheduleId = req.getParameter("scheduleId");
            String taskId = req.getParameter("taskId");

            taskService.detachTaskFromSchedule(scheduleId, taskId);
            logger.info("Task detachded");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
            logger.error("error", ex);
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.error("error", e);
        }
    }
}
