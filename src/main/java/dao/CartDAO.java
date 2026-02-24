package main.java.dao;

import main.java.common.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartDAO {

    // ✅ Add Product to Cart
    public boolean addToCart(int userId, int productId, int quantity) {

        boolean status = false;

        try (Connection con = DatabaseConnection.getConnection()) {

            // Check stock first
            String stockQuery = "SELECT stock FROM products WHERE product_id=?";
            PreparedStatement stockPs = con.prepareStatement(stockQuery);
            stockPs.setInt(1, productId);
            ResultSet stockRs = stockPs.executeQuery();

            if (!stockRs.next()) {
                System.out.println("❌ Invalid Product ID!");
                return false;
            }

            int stock = stockRs.getInt("stock");

            if (quantity > stock) {
                System.out.println("❌ Quantity exceeds stock!");
                return false;
            }

            // Check if already exists in cart
            String checkSql =
                    "SELECT quantity FROM cart WHERE user_id=? AND product_id=?";

            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, userId);
            checkPs.setInt(2, productId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {

                int existingQty = rs.getInt("quantity");

                if (existingQty + quantity > stock) {
                    System.out.println("❌ Cannot add. Stock limit reached!");
                    return false;
                }

                String updateSql =
                        "UPDATE cart SET quantity = quantity + ? WHERE user_id=? AND product_id=?";

                PreparedStatement updatePs = con.prepareStatement(updateSql);
                updatePs.setInt(1, quantity);
                updatePs.setInt(2, userId);
                updatePs.setInt(3, productId);

                status = updatePs.executeUpdate() > 0;

            } else {

                String insertSql =
                        "INSERT INTO cart(user_id, product_id, quantity) VALUES (?, ?, ?)";

                PreparedStatement insertPs = con.prepareStatement(insertSql);
                insertPs.setInt(1, userId);
                insertPs.setInt(2, productId);
                insertPs.setInt(3, quantity);

                status = insertPs.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // ✅ View Cart
    public void viewCart(int userId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            String query =
                    "SELECT c.cart_id, p.name, p.price, c.quantity " +
                            "FROM cart c JOIN products p ON c.product_id = p.product_id " +
                            "WHERE c.user_id=? ORDER BY c.cart_id";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n====== YOUR CART ======");

            boolean empty = true;

            while (rs.next()) {
                empty = false;

                System.out.println(
                        "CartID: " + rs.getInt("cart_id") +
                                " | Product: " + rs.getString("name") +
                                " | Price: ₹" + rs.getDouble("price") +
                                " | Qty: " + rs.getInt("quantity")
                );
            }

            if (empty) {
                System.out.println("🛒 Cart is Empty!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Remove Full Item
    public boolean removeFromCart(int cartId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            String query = "DELETE FROM cart WHERE cart_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cartId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ✅ Update Quantity Directly
    public boolean updateQuantity(int cartId, int newQty) {

        try (Connection con = DatabaseConnection.getConnection()) {

            // Get current quantity + stock
            String query =
                    "SELECT c.quantity, p.stock " +
                            "FROM cart c JOIN products p ON c.product_id=p.product_id " +
                            "WHERE c.cart_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ Invalid Cart ID!");
                return false;
            }

            int currentQty = rs.getInt("quantity");
            int stock = rs.getInt("stock");

            if (newQty > stock) {
                System.out.println("❌ Quantity exceeds stock!");
                return false;
            }

            if (newQty == currentQty) {
                System.out.println("ℹ Quantity unchanged.");
                return true;
            }

            if (newQty <= 0) {

                String delete = "DELETE FROM cart WHERE cart_id=?";
                PreparedStatement ps2 = con.prepareStatement(delete);
                ps2.setInt(1, cartId);
                ps2.executeUpdate();

                System.out.println("🗑 Item Removed from Cart!");
                return true;
            }

            String update =
                    "UPDATE cart SET quantity=? WHERE cart_id=?";

            PreparedStatement ps3 = con.prepareStatement(update);
            ps3.setInt(1, newQty);
            ps3.setInt(2, cartId);

            ps3.executeUpdate();

            System.out.println("✅ Quantity Updated Successfully!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }}