package main.java.service;


import main.java.dao.WishlistDAO;

public class WishlistService {

    private WishlistDAO dao = new WishlistDAO();

    public boolean add(int userId, int productId) {
        return dao.addToWishlist(userId, productId);
    }

    public void view(int userId) {
        dao.viewWishlist(userId);
    }

    public boolean remove(int userId, int productId) {
        return dao.removeWishlistItem(userId, productId);
    }
}
