package main.java.controller;
import main.java.common.Utility;
import main.java.model.Review;
import main.java.service.ReviewService;

public class ReviewController {

    private ReviewService service = new ReviewService();

    // Buyer adds review
    public void addReview(int userId) {

        System.out.println("\n====== ADD REVIEW ======");

        int productId = Utility.readInt("Enter Product ID: ");
        int rating = Utility.readInt("Enter Rating (1 to 5): ");

        String feedback = Utility.readString("Enter Feedback: ");

        Review review = new Review(userId, productId, rating, feedback);

        boolean status = service.submitReview(review);

        if (status) {
            System.out.println("✅ Review Added Successfully!");
        } else {
            System.out.println("❌ Review Failed!");
        }
    }

    // View reviews
    public void viewReviews() {

        int productId = Utility.readInt("Enter Product ID to View Reviews: ");
        service.showReviews(productId);
    }
}
