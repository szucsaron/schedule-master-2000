package com.codecool.web.model;


import java.util.Date;

public class Log {
    private String username;
    private Date date;
    private String type;
    private String location;
    private String msg;

    public Log(String username ,Date date, String type, String location, String msg) {
        this.username = username;
        this.date = date;
        this.type = type;
        this.location = location;
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getMsg() {
        return msg;
    }
}
