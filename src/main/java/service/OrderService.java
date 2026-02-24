package main.java.service;


import main.java.dao.OrderDAO;

public class OrderService {

    private OrderDAO orderDAO = new OrderDAO();

    public boolean checkout(int userId) {
        return orderDAO.placeOrder(userId);
    }
}
