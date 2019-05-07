package com.codecool.web.dao.simple;

import com.codecool.web.dao.ScheduleTaskDao;

import com.codecool.web.model.ScheduleTask;
import com.codecool.web.model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimpleScheduleTaskDao extends AbstractDao implements ScheduleTaskDao {
    public SimpleScheduleTaskDao(Connection connection) {
        super(connection);
    }

    private ScheduleTask fetchScheduleTask(ResultSet resultSet) throws SQLException {
        int taskId = resultSet.getInt("task_id");
        int hourStart = resultSet.getInt("hour_start");
        int hourEnd = resultSet.getInt("hour_start");
        int scheduleId = resultSet.getInt("schedule_id");
        Date date = resultSet.getDate("date");
        LocalDate dateVal = date.toLocalDate();
        String title = resultSet.getString("title");
        String content = resultSet.getString("content");
        return new ScheduleTask(taskId, scheduleId, title, content, dateVal, hourStart, hourEnd);
    }

    public List<ScheduleTask> findAll(int limit) throws SQLException {
        String sql = "SELECT * FROM task RIGHT JOIN schedule_task ON task.id = task_id LIMIT ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            statement.execute();
            List<Task> tasks = new ArrayList<>();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {

            }
            return null;
        }
    }
}

