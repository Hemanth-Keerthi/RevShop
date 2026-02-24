package main.java.service;

import main.java.dao.ProductDAO;
import main.java.model.Product;

public class ProductService {

    private ProductDAO dao = new ProductDAO();

    // Seller Add Product
    public boolean addNewProduct(String name, String category,
                                 double price, int stock, int sellerId) {

        Product p = new Product();
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        p.setStock(stock);
        p.setSellerId(sellerId);

        return dao.addProduct(p);
    }

    // Buyer View All Products
    public void showProducts() {
        dao.viewAllProducts();
    }

    // Seller View Only His Products
    public void showSellerProducts(int sellerId) {
        dao.viewSellerProducts(sellerId);
    }
}