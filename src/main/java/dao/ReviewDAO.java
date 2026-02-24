package main.java.dao;

import main.java.common.DatabaseConnection;
import main.java.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReviewDAO {

    // Add Review
    public boolean addReview(Review review) {

        boolean status = false;

        try {
            Connection con = DatabaseConnection.getConnection();

            String query =
                    "INSERT INTO reviews(user_id,product_id,rating,feedback) " +
                            "VALUES(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getProductId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getFeedback());

            int rows = ps.executeUpdate();

            if (rows > 0) status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    // View Reviews of Product
    public void viewProductReviews(int productId) {

        try {
            Connection con = DatabaseConnection.getConnection();

            String query =
                    "SELECT rating, feedback, review_date " +
                            "FROM reviews WHERE product_id=? " +
                            "ORDER BY review_id DESC";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n====== PRODUCT REVIEWS ======");

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println(
                        "⭐ Rating: " + rs.getInt("rating") +
                                " | Feedback: " + rs.getString("feedback") +
                                " | Date: " + rs.getDate("review_date")
                );
            }

            if (!found) {
                System.out.println("No reviews yet for this product!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
