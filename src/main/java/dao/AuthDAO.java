package main.java.dao;

import main.java.common.DatabaseConnection;
import main.java.model.Authentication;

import java.sql.*;

public class AuthDAO {

    // Register User
    public boolean register(Authentication auth) {

        boolean status = false;

        try {
            Connection con = DatabaseConnection.getConnection();

            String query =
                    "INSERT INTO users(email,password,role) VALUES(?,?,?)";


            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, auth.getEmail());
            ps.setString(2, auth.getPassword());
            ps.setString(3, auth.getRole());

            int rows = ps.executeUpdate(); // ✅ FIX HERE

            if (rows > 0) {
                status = true;
            }
        }
            catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }



    // Login User
    public Authentication login(String email, String password) {

        Authentication user = null;

        try {
            Connection con = DatabaseConnection.getConnection();

            String query = "SELECT * FROM users WHERE email=? AND password=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new Authentication();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
            }

        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Email already registered!");
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return user;
    }
}

