package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.simple.SimpleScheduleDao;
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
import java.time.LocalDate;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/schedules")
public class SchedulesServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Find Schedule by userId

        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new SimpleScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            User loggedInUser = (User) req.getSession().getAttribute("user");
            String userId = String.valueOf(loggedInUser.getId());

            List<Schedule> schedules = scheduleService.findByUser(userId);

            sendMessage(resp, SC_OK, schedules);
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Create Schedule

        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new SimpleScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            String name = req.getParameter("name");
            String date = req.getParameter("date");
            String duration = req.getParameter("duration");

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int userId = loggedInUser.getId();

            scheduleService.add(userId, name, date, duration);
            sendMessage(resp, SC_OK, "new schedule created");
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Modify Schedule (RENAME ONLY)
        // TODO : need to get the schedule's ID

        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new SimpleScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            User loggedInUser = (User) req.getSession().getAttribute("user");
            String userId = String.valueOf(loggedInUser.getId());
            scheduleService.update("1", userId, "updated schedule name here");

            sendMessage(resp, SC_OK, "Schedule's name modified");
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Delete Schedule

        try (Connection connection = getConnection(req.getServletContext())) {
            ScheduleDao scheduleDao = new SimpleScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);

            User loggedInUser = (User) req.getSession().getAttribute("user");
            int userId = loggedInUser.getId();
            String scheduleIdChain = req.getParameter("scheduleIds");
            scheduleService.delete(scheduleIdChain, userId);

            sendMessage(resp, SC_OK, "Schedule deleted");
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }
}
