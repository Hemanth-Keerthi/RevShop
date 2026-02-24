package main.java.dao;

import main.java.common.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BuyerOrderDAO {

    // ✅ View Buyer Orders
    public void viewMyOrders(int userId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            String query =
                    "SELECT order_id, total_amount, status, order_date " +
                            "FROM orders " +
                            "WHERE user_id=? " +
                            "ORDER BY order_id DESC";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n====== MY ORDER HISTORY ======");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "Order ID: " + rs.getInt("order_id") +
                                " | Amount: ₹" + rs.getDouble("total_amount") +
                                " | Status: " + rs.getString("status") +
                                " | Date: " + rs.getTimestamp("order_date")
                );
            }

            if (!found) {
                System.out.println("❌ No orders found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ View Order Items (secure)
    public boolean viewOrderItems(int userId, int orderId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            // Verify order belongs to user
            String check =
                    "SELECT order_id FROM orders WHERE order_id=? AND user_id=?";

            PreparedStatement checkPs = con.prepareStatement(check);
            checkPs.setInt(1, orderId);
            checkPs.setInt(2, userId);

            ResultSet checkRs = checkPs.executeQuery();

            if (!checkRs.next()) {
                System.out.println("❌ Invalid Order ID!");
                return false;
            }

            String query =
                    "SELECT p.name, oi.quantity, p.price " +
                            "FROM order_items oi " +
                            "JOIN products p ON oi.product_id=p.product_id " +
                            "WHERE oi.order_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n====== ORDER ITEMS ======");

            double total = 0;

            while (rs.next()) {

                int qty = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double sub = qty * price;

                System.out.println(
                        "Product: " + rs.getString("name") +
                                " | Qty: " + qty +
                                " | Price: ₹" + price +
                                " | Subtotal: ₹" + sub
                );

                total += sub;
            }

            System.out.println("💰 Order Total: ₹" + total);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ✅ Professional Invoice
    public void generateInvoice(int userId, int orderId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            String orderQuery =
                    "SELECT order_id, total_amount, status, order_date " +
                            "FROM orders WHERE order_id=? AND user_id=?";

            PreparedStatement ps = con.prepareStatement(orderQuery);
            ps.setInt(1, orderId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Invalid Order ID!");
                return;
            }

            System.out.println("\n==============================");
            System.out.println("        REVSHOP INVOICE        ");
            System.out.println("==============================");

            System.out.println("Order ID : " + rs.getInt("order_id"));
            System.out.println("Date     : " + rs.getTimestamp("order_date"));
            System.out.println("Status   : " + rs.getString("status"));
            System.out.println("--------------------------------");

            String itemsQuery =
                    "SELECT p.name, oi.quantity, p.price " +
                            "FROM order_items oi " +
                            "JOIN products p ON oi.product_id=p.product_id " +
                            "WHERE oi.order_id=?";

            PreparedStatement ps2 = con.prepareStatement(itemsQuery);
            ps2.setInt(1, orderId);

            ResultSet rs2 = ps2.executeQuery();

            double total = 0;

            while (rs2.next()) {

                int qty = rs2.getInt("quantity");
                double price = rs2.getDouble("price");
                double sub = qty * price;

                System.out.println(
                        rs2.getString("name") +
                                " | Qty: " + qty +
                                " | ₹" + price +
                                " | ₹" + sub
                );

                total += sub;
            }

            System.out.println("--------------------------------");
            System.out.println("Grand Total : ₹" + total);
            System.out.println("==============================");
            System.out.println("Thank you for shopping at RevShop!");
            System.out.println("==============================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelOrReturnOrder(int userId, int orderId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            con.setAutoCommit(false);

            // STEP 1: Check order
            String checkOrder =
                    "SELECT status FROM orders WHERE order_id=? AND user_id=?";

            PreparedStatement ps1 = con.prepareStatement(checkOrder);
            ps1.setInt(1, orderId);
            ps1.setInt(2, userId);

            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Invalid Order ID!");
                return;
            }

            String status = rs.getString("status");

            // STEP 2: Get payment method
            String paymentQuery =
                    "SELECT method FROM payments WHERE order_id=?";

            PreparedStatement ps2 = con.prepareStatement(paymentQuery);
            ps2.setInt(1, orderId);

            ResultSet payRs = ps2.executeQuery();

            String method = "";
            if (payRs.next()) {
                method = payRs.getString("method");
            }

            // ============================
            // CASE 1: ORDER DELIVERED → RETURN
            // ============================
            if (status.equalsIgnoreCase("DELIVERED")) {

                String update =
                        "UPDATE orders SET status='RETURN_INITIATED' WHERE order_id=?";

                PreparedStatement psUpdate = con.prepareStatement(update);
                psUpdate.setInt(1, orderId);
                psUpdate.executeUpdate();

                con.commit();

                System.out.println("📦 Return Initiated!");
                System.out.println("💰 Refund will be credited after product reaches us.");

                return;
            }

            // ============================
            // CASE 2: NORMAL CANCEL
            // ============================
            if (status.equalsIgnoreCase("PLACED") ||
                    status.equalsIgnoreCase("PAID") ||
                    status.equalsIgnoreCase("PENDING")) {

                String update =
                        "UPDATE orders SET status='CANCELLED' WHERE order_id=?";

                PreparedStatement psUpdate = con.prepareStatement(update);
                psUpdate.setInt(1, orderId);
                psUpdate.executeUpdate();

                // Restore stock
                String items =
                        "SELECT product_id, quantity FROM order_items WHERE order_id=?";

                PreparedStatement psItems = con.prepareStatement(items);
                psItems.setInt(1, orderId);

                ResultSet itemRs = psItems.executeQuery();

                while (itemRs.next()) {

                    String restore =
                            "UPDATE products SET stock = stock + ? WHERE product_id=?";

                    PreparedStatement psRestore = con.prepareStatement(restore);
                    psRestore.setInt(1, itemRs.getInt("quantity"));
                    psRestore.setInt(2, itemRs.getInt("product_id"));
                    psRestore.executeUpdate();
                }

                con.commit();

                System.out.println("❌ Cancelling Initiated!");

                if (method.equalsIgnoreCase("UPI") ||
                        method.equalsIgnoreCase("CARD")) {

                    System.out.println("💰 Refund Initiated!");

                } else if (method.equalsIgnoreCase("COD")) {

                    System.out.println("💵 Order was Cash on Delivery.");
                }

            } else {

                System.out.println("❌ Order cannot be cancelled!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}