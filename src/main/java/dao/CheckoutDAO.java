package main.java.dao;

import main.java.common.DatabaseConnection;
import main.java.service.NotificationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckoutDAO {

    public boolean checkout(int userId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            con.setAutoCommit(false);

            // STEP 1: Check cart items exist
            String cartCheck = "SELECT COUNT(*) FROM cart WHERE user_id=?";
            PreparedStatement checkPs = con.prepareStatement(cartCheck);
            checkPs.setInt(1, userId);
            ResultSet checkRs = checkPs.executeQuery();

            if (checkRs.next() && checkRs.getInt(1) == 0) {
                System.out.println("❌ Cart is empty!");
                return false;
            }

            // STEP 2: Validate stock before order
            String stockCheck =
                    "SELECT c.product_id, c.quantity, p.stock, p.price, p.name " +
                            "FROM cart c JOIN products p ON c.product_id=p.product_id " +
                            "WHERE c.user_id=? FOR UPDATE";

            PreparedStatement stockPs = con.prepareStatement(stockCheck);
            stockPs.setInt(1, userId);
            ResultSet stockRs = stockPs.executeQuery();

            double totalAmount = 0;

            while (stockRs.next()) {

                int qty = stockRs.getInt("quantity");
                int stock = stockRs.getInt("stock");

                if (qty > stock) {
                    System.out.println("❌ Stock not available for product: "
                            + stockRs.getString("name"));
                    con.rollback();
                    return false;
                }

                totalAmount += qty * stockRs.getDouble("price");
            }

            // STEP 3: Insert order
            String orderInsert =
                    "INSERT INTO orders(order_id,user_id,total_amount,status,order_date) " +
                            "VALUES(orders_seq.NEXTVAL,?,?,?,SYSDATE)";

            PreparedStatement orderPs = con.prepareStatement(orderInsert);
            orderPs.setInt(1, userId);
            orderPs.setDouble(2, totalAmount);
            orderPs.setString(3, "PLACED");
            orderPs.executeUpdate();

            // Get generated order id
            String idQuery = "SELECT orders_seq.CURRVAL FROM dual";
            PreparedStatement idPs = con.prepareStatement(idQuery);
            ResultSet idRs = idPs.executeQuery();

            int orderId = 0;
            if (idRs.next()) {
                orderId = idRs.getInt(1);
            }

            // STEP 4: Move cart → order_items + reduce stock
            String cartItems =
                    "SELECT product_id, quantity FROM cart WHERE user_id=?";

            PreparedStatement cartPs = con.prepareStatement(cartItems);
            cartPs.setInt(1, userId);
            ResultSet cartRs = cartPs.executeQuery();

            while (cartRs.next()) {

                int productId = cartRs.getInt("product_id");
                int qty = cartRs.getInt("quantity");

                // Insert order item
                String itemInsert =
                        "INSERT INTO order_items(item_id,order_id,product_id,quantity) " +
                                "VALUES(order_items_seq.NEXTVAL,?,?,?)";

                PreparedStatement itemPs = con.prepareStatement(itemInsert);
                itemPs.setInt(1, orderId);
                itemPs.setInt(2, productId);
                itemPs.setInt(3, qty);
                itemPs.executeUpdate();

                // Reduce stock
                String updateStock =
                        "UPDATE products SET stock = stock - ? WHERE product_id=?";

                PreparedStatement stockUpdate = con.prepareStatement(updateStock);
                stockUpdate.setInt(1, qty);
                stockUpdate.setInt(2, productId);
                stockUpdate.executeUpdate();
            }

            // STEP 5: Clear cart
            String clearCart = "DELETE FROM cart WHERE user_id=?";
            PreparedStatement clearPs = con.prepareStatement(clearCart);
            clearPs.setInt(1, userId);
            clearPs.executeUpdate();

            con.commit();

// STEP 6: Display Full Order Summary

            String summaryQuery =
                    "SELECT p.name, p.price, oi.quantity " +
                            "FROM order_items oi " +
                            "JOIN products p ON oi.product_id = p.product_id " +
                            "WHERE oi.order_id=?";

            PreparedStatement psSummary = con.prepareStatement(summaryQuery);
            psSummary.setInt(1, orderId);
            ResultSet rsSummary = psSummary.executeQuery();

            double total = 0;

            System.out.println("\n====== ORDER SUMMARY ======");
            System.out.println("🧾 Order ID: " + orderId);

            while (rsSummary.next()) {

                int qty = rsSummary.getInt("quantity");
                double price = rsSummary.getDouble("price");
                double sub = qty * price;

                System.out.println(
                        "Product: " + rsSummary.getString("name") +
                                " | Qty: " + qty +
                                " | Price: ₹" + price +
                                " | Subtotal: ₹" + sub
                );

                total += sub;
            }

            System.out.println("💰 Grand Total: ₹" + total);

            // STEP 7: Notification
            NotificationService notify = new NotificationService();
            notify.send(userId, "Your order has been placed successfully!");

            return true;

        } catch (Exception e) {
            System.out.println("❌ Checkout Failed!");
            e.printStackTrace();
        }

        return false;
    }
}