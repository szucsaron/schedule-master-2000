package com.codecool.web.model;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private Role role;

    public User(int id, String name, String password, String email, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
