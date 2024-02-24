package service;

import java.util.concurrent.*;
import com.zaxxer.hikari.HikariDataSource;
import model.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class OrderProcessingService {
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10); // Adjust the pool size based on expected concurrency
    private final HikariDataSource dataSource;

    // Constructor to initialize HikariDataSource
    public OrderProcessingService(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void processOrder(int orderId, int complexityOfExecution) {
        long delayPreparingSeconds = (long) (complexityOfExecution * 60 * 0.1); // Convert minutes to seconds, then calculate 10%
        // Schedule the task to update the order status to the PREPARING after 10% of complexityOfExecution
        executorService.schedule(() -> {
            try {
                updateOrderStatus(orderId, OrderStatus.PREPARING);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, delayPreparingSeconds, TimeUnit.SECONDS);
        // Schedule the task to update the order status to READY after the full complexityOfExecution time
        long complexityOfExecutionSeconds = (long) complexityOfExecution * 60; // Convert minutes to seconds

        executorService.schedule(() -> {
            try {
                updateOrderStatus(orderId, OrderStatus.READY);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, complexityOfExecutionSeconds, TimeUnit.SECONDS);
    }

    // Method to update order status
    private void updateOrderStatus(int orderId, OrderStatus status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE orderId = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.toString());
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }
}
