package com.revhire.job;

import com.revhire.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JobService {

    // ================= EMPLOYER =================

    // Create Job
    public static void createJob(Scanner sc, int employerId) {

        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Description: ");
        String desc = sc.nextLine();
        System.out.print("Skills: ");
        String skills = sc.nextLine();
        System.out.print("Experience: ");
        int exp = Integer.parseInt(sc.nextLine());
        System.out.print("Location: ");
        String location = sc.nextLine();
        System.out.print("Salary: ");
        double salary = Double.parseDouble(sc.nextLine());
        System.out.print("Job Type: ");
        String type = sc.nextLine();

        String sql =
                "INSERT INTO jobs (title, description, skills, experience, location, salary, job_type, status, employer_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, 'OPEN', ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, skills);
            ps.setInt(4, exp);
            ps.setString(5, location);
            ps.setDouble(6, salary);
            ps.setString(7, type);
            ps.setInt(8, employerId);

            ps.executeUpdate();
            System.out.println("✅ Job posted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View jobs posted by employer
    public static void viewMyJobs(int employerId) {

        String sql =
                "SELECT job_id, title, location, salary, status " +
                        "FROM jobs WHERE employer_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                System.out.println("---------------------");
                System.out.println("Job ID: " + rs.getInt("job_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("Status: " + rs.getString("status"));
                found = true;
            }

            if (!found) {
                System.out.println("No jobs posted yet");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Close job
    public static void closeJob(Scanner sc, int employerId) {

        System.out.print("Enter Job ID to close: ");
        int jobId = Integer.parseInt(sc.nextLine());

        String sql =
                "UPDATE jobs SET status='CLOSED' WHERE job_id=? AND employer_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setInt(2, employerId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Job closed successfully");
            } else {
                System.out.println("❌ Job not found or unauthorized");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= JOB SEEKER =================

    // View all open jobs
    public static void viewAllOpenJobs() {

        String sql =
                "SELECT job_id, title, location, salary " +
                        "FROM jobs WHERE status='OPEN'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println("---------------------");
                System.out.println("Job ID: " + rs.getInt("job_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("Salary: " + rs.getDouble("salary"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Search jobs by location
    public static void searchByLocation(String location) {

        String sql =
                "SELECT job_id, title, salary FROM jobs " +
                        "WHERE status='OPEN' AND location=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, location);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                System.out.println("---------------------");
                System.out.println("Job ID: " + rs.getInt("job_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                found = true;
            }

            if (!found) {
                System.out.println("No jobs found for location: " + location);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
