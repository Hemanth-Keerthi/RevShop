package main.java.controller;

import main.java.common.Utility;
import main.java.service.ProductService;

public class ProductController {

    private ProductService productService = new ProductService();

    // ✅ Seller Adds Product
    public void addProduct(int sellerId) {

        System.out.println("\n====== ADD PRODUCT ======");

        String name = Utility.readLine("Enter Product Name: ");
        String category = Utility.readLine("Enter Category: ");
        double price = Utility.readInt("Enter Price: ");
        int stock = Utility.readInt("Enter Stock Quantity: ");

        boolean status = productService.addNewProduct(
                name, category, price, stock, sellerId);
        System.out.println("DEBUG Category Entered: " + category);
        if (status) {
            System.out.println("✅ Product Added Successfully!");
        } else {
            System.out.println("❌ Failed to Add Product!");
        }
    }

    // ✅ Buyer View All Products
    public void viewProducts() {
        productService.showProducts();  // calls DAO viewAllProducts()
    }

    // ✅ Seller View Only His Products
    public void viewSellerProducts(int sellerId) {
        productService.showSellerProducts(sellerId);
    }


}