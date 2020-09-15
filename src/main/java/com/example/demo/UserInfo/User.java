package com.example.demo.UserInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accaunt")
public class User {

    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="role")
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
