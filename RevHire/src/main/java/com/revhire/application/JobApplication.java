package com.revhire.application;

//package application;

import java.time.LocalDate;


public class JobApplication {

    private int applicationId;
    private int jobId;
    private int jobSeekerId;
    private String status; // APPLIED / SHORTLISTED / REJECTED / WITHDRAWN
    private LocalDate appliedDate;

    private String withdrawReason;

    public void withdraw(String reason) {
        this.status = "WITHDRAWN";
        this.withdrawReason = reason;
    }

    public String getWithdrawReason() {
        return withdrawReason;
    }



    public JobApplication(int applicationId, int jobId, int jobSeekerId) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.jobSeekerId = jobSeekerId;
        this.status = "APPLIED";
        this.appliedDate = LocalDate.now();
    }

    public int getApplicationId() {
        return applicationId;
    }

    public int getJobId() {
        return jobId;
    }

    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public void display() {
        System.out.println("Application ID: " + applicationId);
        System.out.println("Job ID: " + jobId);
        System.out.println("Job Seeker ID: " + jobSeekerId);
        System.out.println("Status: " + status);
        System.out.println("Applied On: " + appliedDate);
        System.out.println("-------------------------");
    }
}
