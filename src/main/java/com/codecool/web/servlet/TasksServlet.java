package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.simple.SimpleTaskDao;
import com.codecool.web.model.ScheduleTask;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/task")
public class TasksServlet extends AbstractServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new SimpleTaskDao(connection);
            TaskService taskService = new TaskService(taskDao);
            List<ScheduleTask> scheduleTasks = new ArrayList<>();
            scheduleTasks.add(new ScheduleTask(1, 1, "Lunch", "Going to eat food", LocalDate.parse("2019-05-11"), 12, 13));
            scheduleTasks.add(new ScheduleTask(2, 1, "Doctor's Appointment", "Sore throat", LocalDate.parse("2019-05-10"), 11, 12));
            scheduleTasks.add(new ScheduleTask(3, 1, "Coding", "Meta-refactoring", LocalDate.parse("2019-05-10"), 12, 17));
            scheduleTasks.add(new ScheduleTask(4, 1, "Doing the washing up", "Dirty dishes. They must perish.", LocalDate.parse("2019-05-10"), 17, 19));
            sendMessage(resp, SC_OK, scheduleTasks);




            req.getRequestDispatcher("test.jsp").forward(req, resp);

        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

}