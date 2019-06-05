package com.codecool.web.dao.simple;


import com.codecool.web.dao.LoggerDao;
import com.codecool.web.model.Log;

import java.io.File;
import java.io.FileNotFoundException;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SimpleLoggerDao extends AbstractDao implements LoggerDao {

    public SimpleLoggerDao(Connection connection) {
        super(connection);
    }

    public List<Log> getLogContent() throws FileNotFoundException, ParseException {
        List<Log> logs = new ArrayList<>();
        String[] properties;
        String username ="";
        String hardcodePath = "/home/pgergo/codecool/WEB/apache-tomcat-8.5.38/logs/schmaster.log";
        Scanner s = new Scanner(new File(hardcodePath));
            while (s.hasNextLine()) {
                String line = s.nextLine();
                properties = line.split(";");
                if (properties.length == 7){
                    username = properties[6];
                }
                else if(properties.length==1){
                    continue;
                }
                logs.add(fetchLog(properties, username));
            }
        return logs;
    }

    private Log fetchLog(String[] properties,String username) throws ParseException{
        String date = properties[0].substring(0,properties[0].length()-4);
        String type = properties[1];
        String location = properties[4];
        String msg = properties[5];

        if (properties[1].equals("Logged out")){
        }

        return new Log(username ,dateParser(date),type,location,msg);
    }

    private Date dateParser(String stringDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        Date date = formatter.parse(stringDate);
        return date;
    }

}