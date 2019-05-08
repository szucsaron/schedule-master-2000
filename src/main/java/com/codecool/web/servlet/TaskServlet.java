package com.codecool.web.servlet;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;


import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.simple.SimpleTaskDao;
import com.codecool.web.dto.TaskDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/task")
public class TaskServlet extends AbstractServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new SimpleTaskDao(connection);
            List<TaskDto> scheduleTasks = taskDao.findDtosByScheduleId(5);

            sendMessage(resp, SC_OK, scheduleTasks);
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }

}
