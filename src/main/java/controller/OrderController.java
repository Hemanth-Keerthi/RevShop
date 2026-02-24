package main.java.controller;


import main.java.service.OrderService;

public class OrderController {

    private OrderService orderService = new OrderService();

    public void checkout(int userId) {

        System.out.println("\n====== CHECKOUT ======");

        boolean status = orderService.checkout(userId);

        if (status) {
            System.out.println("✅ Checkout Completed!");
        } else {
            System.out.println("❌ Checkout Failed!");
        }
    }
}

