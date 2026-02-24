package main.java.dao;

import main.java.common.DatabaseConnection;
import main.java.model.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NotificationDAO {

    // Add Notification
    public void addNotification(Notification notification) {

        try {
            Connection con = DatabaseConnection.getConnection();

            String query =
                    "INSERT INTO notifications(user_id,message) VALUES(?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, notification.getUserId());
            ps.setString(2, notification.getMessage());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View Notifications
    public void viewNotifications(int userId) {

        try {
            Connection con = DatabaseConnection.getConnection();

            String query =
                    "SELECT message, created_at " +
                            "FROM notifications " +
                            "WHERE user_id=? " +
                            "ORDER BY notification_id DESC";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n====== NOTIFICATIONS ======");

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("🔔 " + rs.getString("message")
                        + " | " + rs.getDate("created_at"));
            }

            if (!found) {
                System.out.println("No notifications yet!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
