package main.java.menu;

import main.java.common.Utility;
import main.java.controller.*;

public class BuyerMenu {

    public static void showBuyerMenu(int userId) {

        ProductController productController = new ProductController();
        CartController cartController = new CartController();
        CheckoutController checkoutController = new CheckoutController();
        BuyerOrderController orderController = new BuyerOrderController();
        PaymentController paymentController = new PaymentController();
        WishlistController wishlistController = new WishlistController();
        NotificationController notificationController = new NotificationController();
        ReviewController reviewController = new ReviewController();

        while (true) {

            System.out.println("\n====== BUYER DASHBOARD ======");
            System.out.println("1. View Products");
            System.out.println("2. Cart");
            System.out.println("3. Checkout");
            System.out.println("4. Make Payment");
            System.out.println("5. My Orders + Invoice");
            System.out.println("6. Cancel / Return Order");
            System.out.println("7. Wishlist");
            System.out.println("8. View Notifications");
            System.out.println("9. Ratings and Review");
            System.out.println("10. Logout");

            int choice = Utility.readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    productController.viewProducts();
                    break;

                case 2:
                    // ✅ NEW CLEAN CART MENU
                    cartController.showCartMenu(userId);
                    break;

                case 3:
                    checkoutController.checkout(userId);
                    break;

                case 4:
                    paymentController.makePayment(userId);
                    break;

                case 5:
                    orderController.showOrderHistory(userId);
                    break;

                case 6:
                    orderController.cancelOrder(userId);
                    break;

                case 7:
                    showWishlistMenu(userId, wishlistController);
                    break;

                case 8:
                    notificationController.viewMyNotifications(userId);
                    break;

                case 9:
                    showReviewMenu(userId, reviewController);
                    break;

                case 10:
                    System.out.println("Logged out!");
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    // ✅ CLEAN WISHLIST MENU
    private static void showWishlistMenu(int userId, WishlistController wishlistController) {

        while (true) {

            System.out.println("\n====== WISHLIST MENU ======");
            System.out.println("1. Add Product to Wishlist");
            System.out.println("2. View Wishlist");
            System.out.println("3. Remove Item from Wishlist");
            System.out.println("4. Back");

            int choice = Utility.readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    wishlistController.addWishlist(userId);
                    break;

                case 2:
                    wishlistController.viewWishlist(userId);
                    break;

                case 3:
                    wishlistController.removeWishlist(userId);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    // ✅ CLEAN REVIEW MENU
    private static void showReviewMenu(int userId, ReviewController reviewController) {

        while (true) {

            System.out.println("\n====== RATINGS & REVIEW ======");
            System.out.println("1. Add Review");
            System.out.println("2. View Product Reviews");
            System.out.println("3. Back");

            int choice = Utility.readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    reviewController.addReview(userId);
                    break;

                case 2:
                    reviewController.viewReviews();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}