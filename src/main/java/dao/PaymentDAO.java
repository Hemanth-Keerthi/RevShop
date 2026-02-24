package main.java.dao;

import main.java.common.DatabaseConnection;
import main.java.model.Payment;
import main.java.service.NotificationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentDAO {

    public boolean makePayment(Payment payment) {

        boolean status = false;

        try (Connection con = DatabaseConnection.getConnection()) {

            con.setAutoCommit(false);

            // STEP 1: Validate Order
            String amountQuery =
                    "SELECT total_amount FROM orders WHERE order_id=? AND user_id=?";

            PreparedStatement amtPs = con.prepareStatement(amountQuery);
            amtPs.setInt(1, payment.getOrderId());
            amtPs.setInt(2, payment.getUserId());

            ResultSet rs = amtPs.executeQuery();

            double actualAmount;

            if (rs.next()) {
                actualAmount = rs.getDouble("total_amount");
            } else {
                System.out.println("❌ Invalid Order ID!");
                return false;
            }

            // STEP 2: Decide status properly
            String orderStatus;
            String paymentStatus;

            if (payment.getMethod().equalsIgnoreCase("COD")) {

                orderStatus = "PENDING";
                paymentStatus = "PENDING";

            } else {

                orderStatus = "PAID";
                paymentStatus = "SUCCESS";
            }

            // STEP 3: Insert payment
            String insertPayment =
                    "INSERT INTO payments(payment_id,order_id,user_id,amount,method,status,payment_date) " +
                            "VALUES(payments_seq.NEXTVAL,?,?,?,?,?,SYSDATE)";

            PreparedStatement ps = con.prepareStatement(insertPayment);

            ps.setInt(1, payment.getOrderId());
            ps.setInt(2, payment.getUserId());
            ps.setDouble(3, actualAmount);
            ps.setString(4, payment.getMethod());
            ps.setString(5, paymentStatus);

            ps.executeUpdate();

            // STEP 4: Update order
            String updateOrder =
                    "UPDATE orders SET status=? WHERE order_id=?";

            PreparedStatement ps2 = con.prepareStatement(updateOrder);
            ps2.setString(1, orderStatus);
            ps2.setInt(2, payment.getOrderId());

            ps2.executeUpdate();

            con.commit();

            // STEP 5: Send notification (ONLY once)
            NotificationService notify = new NotificationService();

            if (orderStatus.equals("PAID")) {

                notify.send(payment.getUserId(),
                        "Payment successful! Order is now PAID.");

                System.out.println("✅ Order Status Updated to PAID!");

            } else {

                notify.send(payment.getUserId(),
                        "Order placed. Payment will be collected on delivery.");

                System.out.println("✅ Order Status Updated to PENDING!");
            }

            status = true;

        } catch (Exception e) {
            System.out.println("❌ Payment Failed!");
            e.printStackTrace();
        }

        return status;
    }
}