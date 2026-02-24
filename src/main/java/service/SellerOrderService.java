package main.java.service;

import main.java.dao.SellerOrderDAO;

public class SellerOrderService {

    private SellerOrderDAO dao = new SellerOrderDAO();

    // 🔹 Keep this method name EXACTLY as controller expects
    public void viewSellerOrders(int sellerId) {
        dao.viewSellerSales(sellerId);   // call DAO method
    }
}