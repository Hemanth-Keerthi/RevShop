package main.java.dao;

import main.java.common.DatabaseConnection;
import main.java.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDAO {

    // ✅ Add Product
    public boolean addProduct(Product product) {

        boolean status = false;

        try (Connection con = DatabaseConnection.getConnection()) {

            String query =
                    "INSERT INTO products(product_id,name,category,price,stock,seller_id) " +
                            "VALUES(products_seq.NEXTVAL,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory() == null ? "" : product.getCategory().trim());            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setInt(5, product.getSellerId());

            int rows = ps.executeUpdate();
            if (rows > 0) status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
    // ✅ Buyer View All Products
    public void viewAllProducts() {

        try (Connection con = DatabaseConnection.getConnection()) {

            String query =
                    "SELECT product_id, name, NVL(category,'General') AS category, price, stock " +
                            "FROM products ORDER BY product_id DESC";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n====== PRODUCT LIST ======");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        rs.getInt("product_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("category") + " | ₹" +
                                rs.getDouble("price") + " | Stock: " +
                                rs.getInt("stock")
                );
            }

            if (!found) {
                System.out.println("No Products Available.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ✅ Seller View His Products (ALL time)
    public void viewSellerProducts(int sellerId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            String query =
                    "SELECT product_id, name, category, price, stock " +
                            "FROM products WHERE seller_id=? ORDER BY product_id DESC";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, sellerId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n====== YOUR PRODUCTS ======");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        rs.getInt("product_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("category") + " | ₹" +
                                rs.getDouble("price") + " | Stock: " +
                                rs.getInt("stock")
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