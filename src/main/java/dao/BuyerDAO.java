// BuyerDAO.java
package main.java.dao;

import main.java.common.DatabaseConnection;
import main.java.model.Buyer;
import java.sql.*;

public class BuyerDAO {
    public boolean addBuyer(Buyer buyer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO BUYER (ID, NAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, buyer.getId());
            ps.setString(2, buyer.getName());
            ps.setString(3, buyer.getEmail());
            ps.setString(4, buyer.getPassword());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
