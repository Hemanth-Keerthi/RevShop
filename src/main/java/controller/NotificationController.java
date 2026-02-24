package main.java.controller;

import main.java.service.NotificationService;

public class NotificationController {

    private NotificationService service = new NotificationService();

    public void viewMyNotifications(int userId) {
        service.show(userId);
    }

    public void sendNotification(int userId, String msg) {
        service.send(userId, msg);
    }
}
