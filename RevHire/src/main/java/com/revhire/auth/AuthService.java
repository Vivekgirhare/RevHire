package com.revhire.auth;

//import user.*;
import com.revhire.user.Employer;
import com.revhire.user.JobSeeker;
import com.revhire.user.User;
import com.revhire.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class AuthService {

    private static List<User> users = new ArrayList<>();
    private static int userIdCounter = 1;

    // Register Job Seeker
    public static void registerJobSeeker(Scanner sc) {

        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        String sql = "INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, "JOB_SEEKER");

            ps.executeUpdate();
            System.out.println("✅ Job Seeker registered");

        } catch (Exception e) {
            System.out.println("❌ Registration failed");
        }
    }


    // Register Employer
    public static void registerEmployer(Scanner sc) {

        System.out.print("Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        System.out.print("Company Name: ");
        String companyName = sc.nextLine().trim();

        String sql =
                "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, 'EMPLOYER')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();

            System.out.println("✅ Employer registered successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Login
    public static User login(Scanner sc) {

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        String sql =
                "SELECT user_id, name, email, password, role " +
                        "FROM users WHERE email=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email.trim());
            ps.setString(2, password.trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Login successful");
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("❌ Invalid credentials");
        return null;
    }





}
