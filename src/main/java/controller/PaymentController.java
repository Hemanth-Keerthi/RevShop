package main.java.controller;

import main.java.common.Utility;
import main.java.model.Payment;
import main.java.service.PaymentService;

public class PaymentController {

    private PaymentService service = new PaymentService();

    public void makePayment(int userId) {

        System.out.println("\n====== PAYMENT PROCESS ======");

        int orderId = Utility.readInt("Enter Order ID: ");

        System.out.println("Select Payment Method:");
        System.out.println("1. UPI");
        System.out.println("2. CARD");
        System.out.println("3. CASH ON DELIVERY");

        int choice = Utility.readInt("Enter choice: ");

        String method;

        if (choice == 1) {
            method = "UPI";
        }
        else if (choice == 2) {
            method = "CARD";
        }
        else if (choice == 3) {
            method = "COD";
        }
        else {
            System.out.println("❌ Invalid Option!");
            return;
        }

        Payment payment = new Payment(orderId, userId, 0, method, "");

        boolean status = service.pay(payment);

        if (!status) {
            System.out.println("❌ Payment Failed!");
        }
    }
}