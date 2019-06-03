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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/schedule")
public class ScheduleServlet extends AbstractServlet {
    private static final Logger logger = LoggerFactory.getLogger(TaskServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            String scheduleId = req.getParameter("id");
            String isPublic = req.getParameter("isPublic");

            User loggedInUser = (User) req.getSession().getAttribute("user");
            String userId = String.valueOf(loggedInUser.getId());

            ScheduleDao scheduleDao = new SimpleScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);
            TaskDao taskDao = new SimpleTaskDao(connection);

            List<TaskDto> tasks = taskDao.findDtosByScheduleId(Integer.parseInt(scheduleId));
            Schedule schedule;

            if (Boolean.valueOf(isPublic)) {
                schedule = scheduleService.findByScheduleId(scheduleId);
            } else {
                schedule = scheduleService.findById(userId, scheduleId);
            }

            ScheduleTaskDto scheduleTaskDto = new ScheduleTaskDto(schedule, tasks);
            logger.info("Schedule displayed");
            sendMessage(resp, SC_OK, scheduleTaskDto);
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
            logger.error("error", ex);
        }
    }
}
