package com.revhire.user;

public class Employer extends User {

    private String companyName;

    public Employer(int userId, String name, String email, String password, String companyName) {
        super(userId, name, email, password, "EMPLOYER");
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
