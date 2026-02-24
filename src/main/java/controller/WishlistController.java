package main.java.controller;

import main.java.common.Utility;
import main.java.service.WishlistService;

public class WishlistController {

    private WishlistService service = new WishlistService();

    public void addWishlist(int userId) {

        int pid = Utility.readInt("Enter Product ID to add: ");

        boolean status = service.add(userId, pid);

        if (status)
            System.out.println("✅ Added to Wishlist!");
    }
    public void viewWishlist(int userId) {
        service.view(userId);
    }

    public void removeWishlist(int userId) {

        int pid = Utility.readInt("Enter Product ID to remove: ");

        if (service.remove(userId, pid)) {
            System.out.println("✅ Removed from Wishlist!");
        } else {
            System.out.println("❌ Failed to remove!");
        }
    }
}
