package com.revhire.resume;

public class Resume {

    private int jobSeekerId;
    private String objective;
    private String education;
    private String experience;
    private String skills;
    private String projects;

    public Resume(int jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public void display() {
        System.out.println("\n===== RESUME =====");
        System.out.println("Objective: " + objective);
        System.out.println("Education: " + education);
        System.out.println("Experience: " + experience);
        System.out.println("Skills: " + skills);
        System.out.println("Projects: " + projects);
        System.out.println("==================");
    }
}
