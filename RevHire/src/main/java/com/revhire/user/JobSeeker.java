package com.revhire.user;

public class JobSeeker extends User {

    public JobSeeker(int userId, String name, String email, String password) {
        super(userId, name, email, password, "JOB_SEEKER");
    }
}