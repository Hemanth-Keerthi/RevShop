package main.java.controller;

import main.java.common.Utility;
import main.java.model.Authentication;
import main.java.service.AuthService;
import main.java.menu.BuyerMenu;
import main.java.menu.SellerMenu;

public class AuthController {

    private AuthService authService = new AuthService();

    // ================= REGISTER =================
    public void register() {

        System.out.println("\n====== USER REGISTRATION ======");

        String email = Utility.readString("Enter Email: ");
        String password = Utility.readString("Enter Password: ");

        System.out.println("Select Role:");
        System.out.println("1. Buyer");
        System.out.println("2. Seller");

        int choice = Utility.readInt("Enter choice: ");

        String role;

        if (choice == 1) {
            role = "BUYER";
        } else if (choice == 2) {
            role = "SELLER";
        } else {
            System.out.println("❌ Invalid role selection!");
            return;
        }

        boolean status = authService.registerUser(email, password, role);

        if (status) {
            System.out.println("✅ Registration Successful!");
        } else {
            System.out.println("❌ Registration Failed!");
        }
    }

    // ================= LOGIN =================
    public void login() {

        System.out.println("\n====== USER LOGIN ======");

        String email = Utility.readString("Enter Email: ");
        String password = Utility.readString("Enter Password: ");

        Authentication user = authService.loginUser(email, password);

        if (user != null) {

            System.out.println("✅ Login Successful!");
            System.out.println("Welcome " + user.getRole() + " : " + user.getEmail());

            if (user.getRole().equals("BUYER")) {

                BuyerMenu.showBuyerMenu(user.getUserId());

            }
            else if (user.getRole().equals("SELLER")) {
                String businessName = Utility.readString("Enter Business Name: ");
                String gstin = Utility.readString("Enter GSTIN: ");

                // ✅ PASS sellerId properly
                SellerMenu.showSellerMenu(user.getUserId());
            }

        } else {

            System.out.println("❌ Invalid Email or Password!");
        }
    }
}