package main.java.controller;

import main.java.service.SellerOrderService;

public class SellerOrderController {

    private SellerOrderService service = new SellerOrderService();

    public void viewOrders(int sellerId) {
        service.viewSellerOrders(sellerId);
    }
}