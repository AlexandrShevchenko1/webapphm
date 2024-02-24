package repo;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.OrderStatus;
import model.OrderWithDishName;

public class OrderWithDishNameRepository {

    private final HikariDataSource dataSource;

    public OrderWithDishNameRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<OrderWithDishName> getOrdersWithDishNames(int userId) throws SQLException {
        List<OrderWithDishName> ordersWithDishNames = new ArrayList<>();
        String sql = "SELECT o.orderId, o.userId, o.dishId, o.orderDateTime, o.status, d.name AS dishName " +
                "FROM orders o " +
                "JOIN dishes d ON o.dishId = d.id " +
                "WHERE o.userId = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    OrderWithDishName order = new OrderWithDishName();
                    order.setOrderId(resultSet.getInt("orderId"));
                    order.setUserId(resultSet.getInt("userId"));
                    order.setDishId(resultSet.getInt("dishId"));
                    order.setOrderDateTime(resultSet.getTimestamp("orderDateTime"));
                    order.setStatus(OrderStatus.valueOf(resultSet.getString("status").toUpperCase()));
                    order.setDishName(resultSet.getString("dishName"));
                    ordersWithDishNames.add(order);
                }
            }
        }

        return ordersWithDishNames;
    }
}
