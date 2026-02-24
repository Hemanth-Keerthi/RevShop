package main.java.controller;

import main.java.common.Utility;
import main.java.service.CartService;

public class CartController {

    private CartService cartService = new CartService();

    // MAIN CART MENU
    public void showCartMenu(int userId) {

        while (true) {

            System.out.println("\n====== CART MENU ======");
            System.out.println("1. Add Product to Cart");
            System.out.println("2. View Cart");
            System.out.println("3. Manage Cart");
            System.out.println("4. Back");

            int choice = Utility.readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addToCart(userId);
                    break;

                case 2:
                    cartService.showCart(userId);
                    break;

                case 3:
                    manageCart(userId);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    // ADD TO CART
    private void addToCart(int userId) {

        System.out.println("\n====== ADD TO CART ======");

        int productId = Utility.readInt("Enter Product ID: ");
        int qty = Utility.readInt("Enter Quantity: ");

        boolean status = cartService.addProductToCart(userId, productId, qty);

        if (status)
            System.out.println("✅ Product Added to Cart!");
        else
            System.out.println("❌ Failed to Add!");
    }

    // MANAGE CART MENU
    private void manageCart(int userId) {

        while (true) {

            System.out.println("\n====== MANAGE CART ======");
            System.out.println("1. Change Quantity");
            System.out.println("2. Remove Full Item");
            System.out.println("3. Back");

            int choice = Utility.readInt("Enter choice: ");

            switch (choice) {



                case 1:
                    int cartId = Utility.readInt("Enter Cart ID: ");
                    int newQty = Utility.readInt("Enter New Quantity: ");
                    cartService.updateQuantity(cartId, newQty);
                    break;

                case 2:
                    int delId = Utility.readInt("Enter Cart ID to Remove: ");
                    if (cartService.deleteCartItem(delId))
                        System.out.println("✅ Item Removed!");
                    else
                        System.out.println("❌ Remove Failed!");
                    break;

                case 3:
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }

    }
}