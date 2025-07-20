package com.cs336.pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ApplicationDB {

    public ApplicationDB() {
        // Default constructor
    }

    public Connection getConnection() {
        String connectionUrl = "jdbc:mysql://localhost:3306/login_demo";
        Connection connection = null;

        try {
            // Load the MySQL JDBC driver (Connector/J 8+ or 9+)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect using root credentials
            connection = DriverManager.getConnection(connectionUrl, "root", "Gilbert@6737");

        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to the database.");
            e.printStackTrace();
        }

        if (connection == null) {
            System.err.println("❌ Connection object is still null. Check credentials or database status.");
        }

        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("❌ Failed to close connection.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ApplicationDB dao = new ApplicationDB();
        Connection connection = dao.getConnection();

        if (connection != null) {
            System.out.println("✅ Connection successful: " + connection);
        }

        dao.closeConnection(connection);
    }
}