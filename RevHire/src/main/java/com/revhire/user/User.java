package com.revhire.user;

public class User {
    public int userId;
    protected String name;
    protected String email;
    protected String password;
    protected String role; // JOB_SEEKER or EMPLOYER

    public User(int userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }




}
