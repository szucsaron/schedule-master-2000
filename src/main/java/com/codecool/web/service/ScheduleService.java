package com.codecool.web.service;

import com.codecool.web.model.Schedule;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleService {

    List<Schedule> findAll() throws SQLException;

    List<Schedule> findByUser(User user) throws SQLException;

    Schedule findEmptyById(int id) throws SQLException;

    Schedule findFilledById(int id) throws SQLException;

    void add(Task task) throws SQLException;

    void update(Task task) throws SQLException;

    void delete(int id) throws SQLException;

}
