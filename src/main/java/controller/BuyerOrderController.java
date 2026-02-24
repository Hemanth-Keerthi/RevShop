package main.java.controller;

import main.java.common.Utility;
import main.java.service.BuyerOrderService;

public class BuyerOrderController {
    public void cancelOrder(int userId) {

        int orderId = Utility.readInt("Enter Order ID to Cancel/Return: ");
        service.cancelOrder(userId, orderId);
    }

    private BuyerOrderService service = new BuyerOrderService();

    public void showOrderHistory(int userId) {

        service.myOrders(userId);

        int orderId = Utility.readInt("\nEnter Order ID to View Items: ");

        boolean valid = service.orderItems(userId, orderId);

        if (!valid) return;

        int invoiceChoice =
                Utility.readInt("\nGenerate Invoice? (1=Yes, 0=No): ");

        if (invoiceChoice == 1) {
            service.invoice(userId, orderId);
        }
    }
}