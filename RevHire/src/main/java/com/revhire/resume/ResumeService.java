package com.revhire.resume;

import com.revhire.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ResumeService {

    // Create / Update Resume
    public static void createOrUpdateResume(Scanner sc, int jobSeekerId) {

        System.out.print("Objective: ");
        String objective = sc.nextLine();
        System.out.print("Education: ");
        String education = sc.nextLine();
        System.out.print("Experience: ");
        String experience = sc.nextLine();
        System.out.print("Skills: ");
        String skills = sc.nextLine();
        System.out.print("Projects: ");
        String projects = sc.nextLine();

        String sql =
                "REPLACE INTO resumes (job_seeker_id, objective, education, experience, skills, projects) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobSeekerId);
            ps.setString(2, objective);
            ps.setString(3, education);
            ps.setString(4, experience);
            ps.setString(5, skills);
            ps.setString(6, projects);

            ps.executeUpdate();
            System.out.println("✅ Resume saved");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Resume
    public static void viewResume(int jobSeekerId) {

        String sql = "SELECT * FROM resumes WHERE job_seeker_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobSeekerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Objective: " + rs.getString("objective"));
                System.out.println("Education: " + rs.getString("education"));
                System.out.println("Experience: " + rs.getString("experience"));
                System.out.println("Skills: " + rs.getString("skills"));
                System.out.println("Projects: " + rs.getString("projects"));
            } else {
                System.out.println("No resume found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
