package main.java.model;

public class Review {

    private int userId;
    private int productId;
    private int rating;
    private String feedback;

    public Review(int userId, int productId, int rating, String feedback) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.feedback = feedback;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getRating() {
        return rating;
    }



    public String getFeedback() {
        return feedback;
    }
}
