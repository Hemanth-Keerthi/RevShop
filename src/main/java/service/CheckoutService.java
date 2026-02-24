package main.java.service;

import main.java.dao.CheckoutDAO;

public class CheckoutService {

    private CheckoutDAO checkoutDAO = new CheckoutDAO();

    public boolean placeOrder(int userId) {
        return checkoutDAO.checkout(userId);
    }
}
