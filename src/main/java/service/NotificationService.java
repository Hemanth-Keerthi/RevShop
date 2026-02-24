package main.java.service;

import main.java.dao.NotificationDAO;
import main.java.model.Notification;

public class NotificationService {

    private NotificationDAO dao = new NotificationDAO();

    public void send(int userId, String message) {
        dao.addNotification(new Notification(userId, message));
    }

    public void show(int userId) {
        dao.viewNotifications(userId);
    }
}
