package main.java.controller;


import main.java.service.OrderService;


import main.java.service.CheckoutService;

public class CheckoutController {

    private CheckoutService checkoutService = new CheckoutService();

    public void checkout(int userId) {

        System.out.println("\n====== CHECKOUT PROCESS ======");

        boolean status = checkoutService.placeOrder(userId);

        if (status) {
            System.out.println("✅ Checkout Completed Successfully!");
        } else {
            System.out.println("❌ Checkout Failed!");
        }
    }
}
