package repo;

import com.zaxxer.hikari.HikariDataSource;
import model.Dish;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private final HikariDataSource dataSource;

    public OrderRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int addNewOrder(int userId, int dishId, Timestamp orderDateTime, String status) throws SQLException {
        String sql = "INSERT INTO orders (userId, dishId, orderDateTime, status) VALUES (?, ?, ?, ?)";
        int generatedOrderId = 0; // Variable to hold the generated order ID

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, dishId);
            stmt.setTimestamp(3, orderDateTime);
            stmt.setString(4, status.toUpperCase());

            int affectedRows = stmt.executeUpdate();

            // Check if the insert was successful
            if (affectedRows > 0) {
                // Retrieve the generated key (orderId)
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedOrderId = generatedKeys.getInt(1); // Get the generated order ID
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            }
        }
        return generatedOrderId; // Return the generated order ID
    }


    public boolean deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE orderId = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            int affectedRows = stmt.executeUpdate();

            // Return true if the order was successfully deleted
            return affectedRows > 0;
        }
    }

    public int getTotalNumberOfOrders() throws SQLException {
        int totalOrders = 0;
        String sql = "SELECT COUNT(*) AS total_orders FROM orders";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                totalOrders = rs.getInt("total_orders");
            }
        }
        return totalOrders;
    }

    public void deleteAllOrders() throws SQLException {
        String sql = "DELETE FROM orders";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

}
