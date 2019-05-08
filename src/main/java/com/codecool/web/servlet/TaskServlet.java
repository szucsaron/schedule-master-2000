package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleTaskDao;
import com.codecool.web.dao.simple.SimpleScheduleTaskDao;
import com.codecool.web.model.ScheduleTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/task")
public class TaskServlet extends AbstractServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleTaskDao taskDao = new SimpleScheduleTaskDao(connection);
            List<ScheduleTask> scheduleTasks = taskDao.findByScheduleId(5);

            sendMessage(resp, SC_OK, scheduleTasks);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

}