package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.simple.SimpleScheduleDao;
import com.codecool.web.dao.simple.SimpleTaskDao;
import com.codecool.web.dto.ScheduleTaskDto;
import com.codecool.web.dto.TaskDto;
import com.codecool.web.model.Schedule;
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

@WebServlet("/share")
public class ShareServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Find shared schedule by id
        try (Connection connection = getConnection(req.getServletContext())) {
            String scheduleId = req.getParameter("schedule_id");
            ScheduleDao scheduleDao = new SimpleScheduleDao(connection);
            ScheduleService scheduleService = new SimpleScheduleService(scheduleDao);
            TaskDao taskDao = new SimpleTaskDao(connection);

            Schedule schedule = scheduleService.fetchShared(scheduleId);
            List<TaskDto> tasks = taskDao.findDtosByScheduleId(Integer.parseInt(scheduleId));

            ScheduleTaskDto scheduleTaskDto = new ScheduleTaskDto(schedule, tasks);

            sendMessage(resp, SC_OK, scheduleTaskDto);
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }
}
