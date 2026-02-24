package main.java.service;

import main.java.common.DatabaseConnection;
import main.java.dao.PaymentDAO;
import main.java.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentService {

    private PaymentDAO dao = new PaymentDAO();

    public boolean pay(Payment payment) {
        return dao.makePayment(payment);
    }

    /*public boolean processPayment(int orderId, int buyerId, double amount, String method) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO payments (order_id, buyer_id, amount, method, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            stmt.setInt(2, buyerId);
            stmt.setDouble(3, amount);
            stmt.setString(4, method);
            stmt.setString(5, "SUCCESS");
            stmt.executeUpdate();
            System.out.println("✅ Payment processed successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("Error processing payment: " + e.getMessage());
            return false;
        }
    }*/
}
