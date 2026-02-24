package main.java.dao;

import main.java.common.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WishlistDAO {

    // ✅ Add to Wishlist (Fixed Version)
    public boolean addToWishlist(int userId, int productId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            // STEP 1: Check product exists
            String productCheck =
                    "SELECT product_id FROM products WHERE product_id=?";

            PreparedStatement ps1 = con.prepareStatement(productCheck);
            ps1.setInt(1, productId);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                System.out.println("❌ Invalid Product ID!");
                return false;
            }

            // STEP 2: Check duplicate
            String duplicateCheck =
                    "SELECT 1 FROM wishlist WHERE user_id=? AND product_id=?";

            PreparedStatement ps2 = con.prepareStatement(duplicateCheck);
            ps2.setInt(1, userId);
            ps2.setInt(2, productId);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                System.out.println("ℹ Item already in Wishlist!");
                return false;
            }

            // STEP 3: Insert
            String insert =
                    "INSERT INTO wishlist(user_id,product_id) VALUES(?,?)";

            PreparedStatement ps3 = con.prepareStatement(insert);
            ps3.setInt(1, userId);
            ps3.setInt(2, productId);

            ps3.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ✅ View Wishlist
    public void viewWishlist(int userId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            String query =
                    "SELECT p.product_id, p.name, p.price " +
                            "FROM wishlist w " +
                            "JOIN products p ON w.product_id=p.product_id " +
                            "WHERE w.user_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n====== MY WISHLIST ======");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "Product ID: " + rs.getInt("product_id") +
                                " | Name: " + rs.getString("name") +
                                " | Price: ₹" + rs.getDouble("price")
                );
            }

            if (!found) {
                System.out.println("Wishlist is empty!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Remove from Wishlist
    public boolean removeWishlistItem(int userId, int productId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            String query =
                    "DELETE FROM wishlist WHERE user_id=? AND product_id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setInt(2, productId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                return true;
            } else {
                System.out.println("❌ Item not found in wishlist!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}