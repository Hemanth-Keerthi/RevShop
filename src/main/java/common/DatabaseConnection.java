package main.java.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    // Private constructor (Singleton Design)
    private DatabaseConnection() {
    }

    // Method to Get Oracle Connection
    public static Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {

                // ✅ Step 1: Load Oracle JDBC Driver
                Class.forName("oracle.jdbc.driver.OracleDriver");

                // ✅ Step 2: Create Connection
                connection = DriverManager.getConnection(
                        Constants.DB_URL,
                        Constants.DB_USERNAME,
                        Constants.DB_PASSWORD
                );

                //System.out.println("✅ Oracle Database Connected Successfully!");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("❌ Oracle JDBC Driver Not Found!");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed!");
            e.printStackTrace();
        }

        return connection;
    }
}
