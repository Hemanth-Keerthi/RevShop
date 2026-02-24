package main.java.service;

import main.java.dao.ReviewDAO;
import main.java.model.Review;

public class ReviewService {

    private ReviewDAO dao = new ReviewDAO();

    public boolean submitReview(Review review) {
        return dao.addReview(review);
    }

    public void showReviews(int productId) {
        dao.viewProductReviews(productId);
    }
}
