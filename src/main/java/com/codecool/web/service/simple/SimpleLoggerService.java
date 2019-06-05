package com.codecool.web.service.simple;

import com.codecool.web.dao.LoggerDao;
import com.codecool.web.model.Log;
import com.codecool.web.service.LoggerService;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

//import javafx.concurrent.Service;


public final class SimpleLoggerService implements LoggerService {

    private final LoggerDao loggerDao;

    public SimpleLoggerService(LoggerDao loggerDao) {
        this.loggerDao = loggerDao;
    }

    @Override
    public List<Log> getLogContent() throws FileNotFoundException, ParseException {
        return loggerDao.getLogContent();
    }
}

