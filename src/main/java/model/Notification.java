package main.java.model;


public class Notification {

    private int userId;
    private String message;

    public Notification(int userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
