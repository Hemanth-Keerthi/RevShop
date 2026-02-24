package main.java.dao;

import main.java.common.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderDAO {

    // Place Order
    public boolean placeOrder(int userId) {

        boolean status = false;

        try {
            Connection con = DatabaseConnection.getConnection();

            // Step 1: Calculate total from cart
            String totalQuery =
                    "SELECT SUM(p.price * c.quantity) AS total " +
                            "FROM cart c JOIN products p ON c.product_id=p.product_id " +
                            "WHERE c.user_id=?";

            PreparedStatement ps1 = con.prepareStatement(totalQuery);
            ps1.setInt(1, userId);

            ResultSet rs = ps1.executeQuery();

            double totalAmount = 0;
            if (rs.next()) {
                totalAmount = rs.getDouble("total");
            }

            if (totalAmount == 0) {
                System.out.println("❌ Cart is empty!");
                return false;
            }

            // Step 2: Insert into orders table
            String orderQuery =
                    "INSERT INTO orders(order_id,user_id,total_amount,status) " +
                            "VALUES(orders_seq.NEXTVAL,?,?,?)";

            PreparedStatement ps2 = con.prepareStatement(orderQuery);

            ps2.setInt(1, userId);
            ps2.setDouble(2, totalAmount);
            ps2.setString(3, "PLACED");

            int rows = ps2.executeUpdate();

            if (rows > 0) status = true;

            // Step 3: Clear cart after checkout
            String clearCart = "DELETE FROM cart WHERE user_id=?";
            PreparedStatement ps3 = con.prepareStatement(clearCart);
            ps3.setInt(1, userId);
            ps3.executeUpdate();

            System.out.println("✅ Order Placed Successfully!");
            System.out.println("Total Amount Paid: ₹" + totalAmount);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
