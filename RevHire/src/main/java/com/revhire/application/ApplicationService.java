package com.revhire.application;
import com.revhire.notification.NotificationService;
import com.revhire.util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class ApplicationService {

    // ================= JOB SEEKER =================

    // Apply for Job
    public static void applyJob(int jobId, int jobSeekerId) {

        String sql =
                "INSERT INTO applications (job_id, job_seeker_id, status, applied_date) " +
                        "VALUES (?, ?, 'APPLIED', CURDATE())";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setInt(2, jobSeekerId);
            ps.executeUpdate();

//            System.out.println("✅ Job applied successfully");
            logger.info("Job applied successfully");


        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ You already applied for this job");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View My Applications
    public static void viewMyApplications(int jobSeekerId) {

        String sql =
                "SELECT application_id, job_id, status, applied_date " +
                        "FROM applications WHERE job_seeker_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobSeekerId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                System.out.println("----------------------");
                System.out.println("Application ID: " + rs.getInt("application_id"));
                System.out.println("Job ID: " + rs.getInt("job_id"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Applied Date: " + rs.getDate("applied_date"));
                found = true;
            }

            if (!found) {
                System.out.println("No applications found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Withdraw Application
    public static void withdrawApplication(Scanner sc, int jobSeekerId) {

        System.out.print("Enter Application ID: ");
        int appId = Integer.parseInt(sc.nextLine());

        System.out.print("Reason (optional): ");
        String reason = sc.nextLine();

        String sql =
                "UPDATE applications SET status='WITHDRAWN', withdraw_reason=? " +
                        "WHERE application_id=? AND job_seeker_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reason);
            ps.setInt(2, appId);
            ps.setInt(3, jobSeekerId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Application withdrawn");
            } else {
//                System.out.println("❌ Application not found");
                logger.warn("Application not found");


            }

        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("Error while applying job", e);

        }
    }

    // ================= EMPLOYER =================

    // View Applicants
    public static void viewApplicants(int employerId) {

        String sql =
                "SELECT a.application_id, u.user_id, u.name, a.status, " +
                        "r.objective, r.education, r.experience, r.skills, r.projects " +
                        "FROM applications a " +
                        "JOIN jobs j ON a.job_id = j.job_id " +
                        "JOIN users u ON a.job_seeker_id = u.user_id " +
                        "LEFT JOIN resumes r ON u.user_id = r.job_seeker_id " +
                        "WHERE j.employer_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                System.out.println("=================================");
                System.out.println("Application ID : " + rs.getInt("application_id"));
                System.out.println("Job Seeker ID  : " + rs.getInt("user_id"));
                System.out.println("Name           : " + rs.getString("name"));
                System.out.println("Status         : " + rs.getString("status"));

                System.out.println("\n----- Resume -----");
                System.out.println("Objective  : " + rs.getString("objective"));
                System.out.println("Education  : " + rs.getString("education"));
                System.out.println("Experience : " + rs.getString("experience"));
                System.out.println("Skills     : " + rs.getString("skills"));
                System.out.println("Projects   : " + rs.getString("projects"));
                System.out.println("===============================\n");

                found = true;
            }

            if (!found) {
                System.out.println("No applicants found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Shortlist / Reject
    public static void updateApplicationStatus(Scanner sc) {

        while (true) {

            System.out.print("\nEnter Application ID (0 to go back): ");
            String input = sc.nextLine().trim();

            if (input.equals("0")) return;

            int appId;
            try {
                appId = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("❌ Invalid input");
                continue;
            }

            System.out.println("1. Shortlist");
            System.out.println("2. Reject");
            System.out.println("0. Back");
            System.out.print("Choose option: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("0")) return;

            String status;
            if (choice.equals("1")) status = "SHORTLISTED";
            else if (choice.equals("2")) status = "REJECTED";
            else {
                System.out.println("❌ Invalid option");
                continue;
            }

            String updateSql =
                    "UPDATE applications SET status=? WHERE application_id=?";

            String notifySql =
                    "INSERT INTO notifications(user_id, message, created_at) " +
                            "SELECT job_seeker_id, ?, CURDATE() FROM applications WHERE application_id=?";

            try (Connection con = DBConnection.getConnection()) {

                PreparedStatement ps1 = con.prepareStatement(updateSql);
                ps1.setString(1, status);
                ps1.setInt(2, appId);

                int rows = ps1.executeUpdate();

                if (rows == 0) {
                    System.out.println("❌ Application not found");
                    return;
                }

                PreparedStatement ps2 = con.prepareStatement(notifySql);
                ps2.setString(1, "Your application has been " + status);
                ps2.setInt(2, appId);
                ps2.executeUpdate();

                System.out.println("✅ Status updated to " + status);
                System.out.println("📩 Notification sent to job seeker");

                return;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static final Logger logger =
            LogManager.getLogger(ApplicationService.class);




}
