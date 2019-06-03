package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.simple.SimpleScheduleDao;
import com.codecool.web.dao.simple.SimpleTaskDao;
import com.codecool.web.dto.ScheduleTaskDto;
import com.codecool.web.dto.TaskDto;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.ScheduleService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleScheduleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/shareUpdate")
public class ShareUpdateServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Create Schedule

        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new SimpleScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            String id = req.getParameter("schedule_id");
            String isPublic = req.getParameter("is_public");

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int userId = loggedInUser.getId();

            scheduleService.setPublic(id, isPublic);
            sendMessage(resp, SC_OK, "new schedule created");
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }
}
