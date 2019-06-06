package com.codecool.web.servlet;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.dao.simple.SimpleTaskDao;
import com.codecool.web.dto.TaskDto;
import com.codecool.web.model.Task;
import com.codecool.web.service.TaskService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleTaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/test")
public class TestServlet extends AbstractServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new SimpleTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);
            TaskDto task = taskService.findDtoById("1", "3");
            List<TaskDto> dtos = taskService.findDtosByScheduleId("7");
            String path = req.getServletContext().getRealPath("/");
            FileReader fileReader = new FileReader(path + "../../logs/schmaster.log");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            StringBuilder sb = new StringBuilder();

            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }


            sendMessage(resp, SC_OK, sb.toString());
            req.getRequestDispatcher(".test/index.jsp").forward(req, resp);

        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try (Connection connection = getConnection(req.getServletContext())) {
            TaskDao taskDao = new SimpleTaskDao(connection);
            TaskService taskService = new SimpleTaskService(taskDao);
            TaskDto task = taskService.findDtoById("1", "3");
            List<TaskDto> dtos = taskService.findDtosByScheduleId("7");

            sendMessage(resp, SC_OK, dtos);
        } catch (SQLException | ServiceException ex) {
            handleSqlError(resp, ex);
        }
    }

}