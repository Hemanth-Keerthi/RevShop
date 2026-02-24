package main.java.dao;

import main.java.common.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SellerOrderDAO {

    // ✅ Seller Sales History (Past + Present)
    public void viewSellerSales(int sellerId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            String sql =
                    "SELECT name, stock, price, (stock * price) AS total_value " +
                            "FROM products WHERE seller_id=? ORDER BY product_id DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sellerId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n====== MY PRODUCTS SUMMARY ======");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        "Product: " + rs.getString("name") +
                                " | Stock: " + rs.getInt("stock") +
                                " | Unit Price: ₹" + rs.getDouble("price") +
                                " | Total Value: ₹" + rs.getDouble("total_value")
                );
            }

            if (!found) {
                System.out.println("No Products Found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}