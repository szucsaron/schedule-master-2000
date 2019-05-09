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

@WebServlet("/addTask")
public class AddTaskServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Find Schedule by userId
        try (Connection connection = getConnection(req.getServletContext())) {
            String scheduleId = req.getParameter("scheduleId");

            User loggedInUser = (User) req.getSession().getAttribute("user");
            String userId = String.valueOf(loggedInUser.getId());

            TaskDao taskDao = new SimpleTaskDao(connection);

            List<Task> tasks = taskDao.findUnscheduledDtosByScheduleId(Integer.parseInt(scheduleId));


            sendMessage(resp, SC_OK, tasks);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Find Schedule by userId
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new SimpleTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);
            String scheduleId = req.getParameter("scheduleId");
            String taskId = req.getParameter("taskId");
            String day = req.getParameter("col");
            String hourStart = req.getParameter("row");
            String hourEnd = req.getParameter("row");

            taskService.attachTaskToSchedule(scheduleId, taskId, day, hourStart, hourEnd);

            ScheduleServlet scheduleServlet = new ScheduleServlet();
            scheduleServlet.doGet(req, resp);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
