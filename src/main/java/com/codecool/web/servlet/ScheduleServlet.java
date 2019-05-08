package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.simple.SimpleScheduleDao;
import com.codecool.web.dao.simple.SimpleTaskDao;
import com.codecool.web.dto.TaskDto;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.simple.SimpleScheduleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/schedule")
public class ScheduleServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Find Schedule by userId
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new SimpleTaskDao(connection);
            List<TaskDto> tasks = taskDao.findDtosByScheduleId(1);


            sendMessage(resp, SC_OK, "fasz");
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }


}
