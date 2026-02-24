package main.java.service;

import main.java.dao.AuthDAO;
import main.java.model.Authentication;
import main.java.util.PasswordUtil;
import main.java.util.ValidationUtil;

public class AuthService {

    private AuthDAO authDAO = new AuthDAO();

    // Register Logic
    public boolean registerUser(String email, String password, String role) {

        if (!ValidationUtil.isValidEmail(email)) {
            System.out.println("❌ Invalid Email Format!");
            return false;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            System.out.println("❌ Password must be at least 6 characters!");
            return false;
        }

        // Hash password before saving
        String hashedPassword = PasswordUtil.hashPassword(password);

        Authentication auth = new Authentication(email, hashedPassword, role);

        return authDAO.register(auth);
    }

    // Login Logic
    public Authentication loginUser(String email, String password) {

        String hashedPassword = PasswordUtil.hashPassword(password);

        return authDAO.login(email, hashedPassword);
    }
}
