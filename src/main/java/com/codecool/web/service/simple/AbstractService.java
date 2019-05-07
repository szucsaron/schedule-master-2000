package com.codecool.web.service.simple;

import com.codecool.web.service.exception.ServiceException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AbstractService {
    protected int fetchInt(String intStr, String msgVarName) throws ServiceException{
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            throw new ServiceException(msgVarName + " must be an integer");
        }
    }

    protected LocalDate fetchDate(String dateStr, String msgVarName) throws ServiceException {
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new ServiceException("Date must follow the correct format: yyyy-mm-dd");
        }
    }
}
