package main.java.service;

import main.java.dao.CartDAO;

public class CartService {

    private CartDAO cartDAO = new CartDAO();

    public boolean addProductToCart(int userId, int productId, int qty) {
        return cartDAO.addToCart(userId, productId, qty);
    }

    public void showCart(int userId) {
        cartDAO.viewCart(userId);
    }

    public boolean deleteCartItem(int cartId) {
        return cartDAO.removeFromCart(cartId);
    }

    public boolean updateQuantity(int cartId, int qty) {
        return cartDAO.updateQuantity(cartId, qty);
    }
}