package com.example.demo.UserInfo;

public class User {

    private String username;
    private String password;
    private String roles;

    public User(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public CharSequence getPassword() {
        return password;
    }

    public String getRoles() {
        return roles;
    }
    public String getName() {
        return username;
    }
}
