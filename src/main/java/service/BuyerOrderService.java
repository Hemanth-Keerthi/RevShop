package main.java.service;

import main.java.dao.BuyerOrderDAO;

public class BuyerOrderService {

    private BuyerOrderDAO dao = new BuyerOrderDAO();

    public void myOrders(int userId) {
        dao.viewMyOrders(userId);
    }

    public boolean orderItems(int userId, int orderId) {
        return dao.viewOrderItems(userId, orderId);
    }

    public void invoice(int userId, int orderId) {
        dao.generateInvoice(userId, orderId);
    }

public void cancelOrder(int userId, int orderId) {
    dao.cancelOrReturnOrder(userId, orderId);

}
}
