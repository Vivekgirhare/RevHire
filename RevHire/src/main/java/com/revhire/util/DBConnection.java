package com.revhire.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/revhire";
    private static final String USER = "root";
    private static final String PASSWORD = "Vivekg123@";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("❌ Database connection failed");
            e.printStackTrace();
            return null;
        }
    }
}
