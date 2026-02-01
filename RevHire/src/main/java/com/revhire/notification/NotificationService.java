package com.revhire.notification;
import com.revhire.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private static List<Notification> notifications = new ArrayList<>();

    public static void notifyUser(int userId, String message) {
        notifications.add(new Notification(userId, message));
    }

    // Simplified employer notification
    public static void notifyEmployer(int jobId, String message) {
        notifications.add(new Notification(0, message));
    }

    public static void viewNotifications(int userId) {

        String sql =
                "SELECT message, created_at FROM notifications WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                System.out.println("🔔 " + rs.getString("message")
                        + " (" + rs.getDate("created_at") + ")");
                found = true;
            }

            if (!found) {
                System.out.println("No notifications available");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
