package com.revhire.notification;
import java.time.LocalDateTime;

public class Notification {

    private int userId;
    private String message;
    private LocalDateTime time;

    public Notification(int userId, String message) {
        this.userId = userId;
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public int getUserId() {
        return userId;
    }

    public void display() {
        System.out.println("🔔 " + message + " (" + time + ")");
    }
}
