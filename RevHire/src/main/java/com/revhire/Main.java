package com.revhire;
import com.revhire.auth.AuthService;
import com.revhire.application.ApplicationService;
import com.revhire.job.JobService;
import com.revhire.notification.NotificationService;
import com.revhire.resume.ResumeService;
import com.revhire.user.User;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== RevHire Job Portal =====");
            System.out.println("1. Register Job Seeker");
            System.out.println("2. Register Employer");
            System.out.println("3. Login");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    AuthService.registerJobSeeker(sc);
                    break;

                case 2:
                    AuthService.registerEmployer(sc);
                    break;

                case 3:
                    handleLogin(sc);
                    break;

                case 4:
                    System.out.println("Thank you for using RevHire!");
                    return;

                default:
                    System.out.println("❌ Invalid option");
            }
        }
    }

    // ================= LOGIN HANDLER =================
    private static void handleLogin(Scanner sc) {

        User user = AuthService.login(sc);

        if (user == null) {
            return;
        }

        if ("EMPLOYER".equals(user.getRole())) {
            employerMenu(sc, user.getUserId());
        } else {
            jobSeekerMenu(sc, user.getUserId());
        }
    }

    // ================= EMPLOYER DASHBOARD =================
    private static void employerMenu(Scanner sc, int employerId) {

        while (true) {
            System.out.println("\n===== Employer Dashboard =====");
            System.out.println("1. Post Job");
            System.out.println("2. View My Jobs");
            System.out.println("3. Close Job");
            System.out.println("4. View Applicants");
            System.out.println("5. Shortlist / Reject Applicant");
            System.out.println("6. View Notifications");
            System.out.println("7. Logout");
            System.out.println("8. Back");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 10) {
                return;
            }

            switch (choice) {
                case 1:
                    JobService.createJob(sc, employerId);
                    break;

                case 2:
                    JobService.viewMyJobs(employerId);
                    break;

                case 3:
                    JobService.closeJob(sc, employerId);
                    break;

                case 4:
                    ApplicationService.viewApplicants(employerId);
                    break;

                case 5:
                    ApplicationService.updateApplicationStatus(sc);
                    break;

                case 6:
                    NotificationService.viewNotifications(employerId);
                    break;

                case 7:
                    System.out.println("Logged out successfully");
                    return;

                default:
                    System.out.println("❌ Invalid option");
            }
        }
    }

    // ================= JOB SEEKER DASHBOARD =================
    private static void jobSeekerMenu(Scanner sc, int jobSeekerId) {

        while (true) {
            System.out.println("\n===== Job Seeker Dashboard =====");
            System.out.println("1. Create / Update Resume");
            System.out.println("2. View Resume");
            System.out.println("3. View All Jobs");
            System.out.println("4. Search Jobs by Location");
            System.out.println("5. Apply for Job");
            System.out.println("6. View My Applications");
            System.out.println("7. Withdraw Application");
            System.out.println("8. View Notifications");
            System.out.println("9. Logout");
            System.out.println("10. Back");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 10) {
                return;   // 👈 goes back to previous menu
            }

            switch (choice) {
                case 1:
                    ResumeService.createOrUpdateResume(sc, jobSeekerId);
                    break;

                case 2:
                    ResumeService.viewResume(jobSeekerId);
                    break;

                case 3:
                    JobService.viewAllOpenJobs();
                    break;

                case 4:
                    System.out.print("Enter location: ");
                    JobService.searchByLocation(sc.nextLine());
                    break;

                case 5:
                    System.out.print("Enter Job ID to apply: ");
                    int jobId = Integer.parseInt(sc.nextLine());
                    ApplicationService.applyJob(jobId, jobSeekerId);
                    break;

                case 6:
                    ApplicationService.viewMyApplications(jobSeekerId);
                    break;

                case 7:
                    ApplicationService.withdrawApplication(sc, jobSeekerId);
                    break;

                case 8:
                    NotificationService.viewNotifications(jobSeekerId);
                    break;

                case 9:
                    System.out.println("Logged out successfully");
                    return;

                default:
                    System.out.println("❌ Invalid option");
            }
        }
    }
}
