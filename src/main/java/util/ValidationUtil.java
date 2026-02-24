package main.java.util;


public class ValidationUtil {

    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.endsWith(".com");
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
}

