package main.java.model;



public class Payment {

    private int orderId;
    private int userId;
    private double amount;
    private String method;
    private String status;

    public Payment() {}

    public Payment(int orderId, int userId, double amount, String method, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }
}
