package main.java.menu;

import main.java.common.Utility;
import main.java.controller.ProductController;
import main.java.controller.SellerOrderController;

public class SellerMenu {

    public static void showSellerMenu(int currentSellerId) {

        ProductController productController = new ProductController();
        SellerOrderController orderController = new SellerOrderController();

        while (true) {

            System.out.println("\n====== SELLER DASHBOARD ======");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. View My Orders");
            System.out.println("4. Logout");

            int choice = Utility.readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    productController.addProduct(currentSellerId);
                    break;

                case 2:
                    productController.viewSellerProducts(currentSellerId);
                    break;

                case 3:
                    SellerOrderController sellerOrderController = new SellerOrderController();
                    sellerOrderController.viewOrders(currentSellerId);
                    break;

                case 4:
                    System.out.println("Logged out!");
                    return;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }
}