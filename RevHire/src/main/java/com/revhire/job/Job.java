package com.revhire.job;

//package job;

public class Job {

    private int jobId;
    private String title;
    private String description;
    private String skills;
    private int experience;
    private String location;
    private double salary;
    private String jobType;
    private String status; // OPEN / CLOSED
    private int employerId;

    public Job(int jobId, String title, String description, String skills,
               int experience, String location, double salary,
               String jobType, int employerId) {

        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.experience = experience;
        this.location = location;
        this.salary = salary;
        this.jobType = jobType;
        this.employerId = employerId;
        this.status = "OPEN";
    }

    public int getEmployerId() {
        return employerId;
    }

    public String getStatus() {
        return status;
    }

    public void closeJob() {
        this.status = "CLOSED";
    }

    public int getJobId() {
        return jobId;
    }

    public String getLocation() {
        return location;
    }


    public void displayJob() {
        System.out.println("Job ID: " + jobId);
        System.out.println("Title: " + title);
        System.out.println("Location: " + location);
        System.out.println("Experience: " + experience + " years");
        System.out.println("Salary: " + salary);
        System.out.println("Type: " + jobType);
        System.out.println("Status: " + status);
        System.out.println("----------------------");
    }
}
