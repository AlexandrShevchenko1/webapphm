package repo;
import model.Dish;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;

public class DishRepository {

    private final HikariDataSource dataSource;

    public DishRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dish> getDishes() throws SQLException {
        List<Dish> dishes = new ArrayList<>();
        String sql = "SELECT * FROM dishes";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDescription(rs.getString("description"));
                dish.setQuantity(rs.getInt("quantity"));
                dish.setPrice(rs.getBigDecimal("price"));
                dish.setComplexityOfExecution(rs.getInt("complexity_of_execution"));
                dishes.add(dish);
            }
        }
        return dishes;
    }

    public List<Dish> getAvailableDishes() throws SQLException {
        List<Dish> availableDishes = new ArrayList<>();
        String sql = "SELECT * FROM dishes WHERE isAvailable = TRUE";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDescription(rs.getString("description"));
                dish.setQuantity(rs.getInt("quantity"));
                dish.setPrice(rs.getBigDecimal("price"));
                dish.setComplexityOfExecution(rs.getInt("complexity_of_execution"));
                // Assuming you add the isAvailable field to your Dish model
                dish.setIsAvailable(rs.getBoolean("isAvailable"));
                availableDishes.add(dish);
            }
        }
        return availableDishes;
    }
    public void addNewDish(String name, String description, String quantity, String price, String complexity) throws SQLException {
        String sql = "INSERT INTO dishes (name, description, quantity, price, complexity_of_execution) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, Integer.parseInt(quantity));
            stmt.setBigDecimal(4, new java.math.BigDecimal(price));
            stmt.setInt(5, Integer.parseInt(complexity));
            stmt.executeUpdate();
        }
    }

    public void decreaseQuantity(int dishId) throws SQLException {
        // Decrease the quantity by 1 and check if the quantity has reached 0
        String updateQuantitySql = "UPDATE dishes SET quantity = quantity - 1 WHERE id = ? AND quantity > 0 RETURNING quantity";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateQuantitySql)) {
            updateStmt.setInt(1, dishId);
            try (ResultSet resultSet = updateStmt.executeQuery()) {
                // Check if the update was successful and the quantity is now 0
                if (resultSet.next() && resultSet.getInt("quantity") <= 0) {
                    // Set isAvailable to FALSE for this dish
                    String updateAvailabilitySql = "UPDATE dishes SET isAvailable = FALSE WHERE id = ?";
                    try (PreparedStatement updateAvailabilityStmt = conn.prepareStatement(updateAvailabilitySql)) {
                        updateAvailabilityStmt.setInt(1, dishId);
                        updateAvailabilityStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public void deleteDish(int dishId) throws SQLException {
        String sql = "DELETE FROM dishes WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dishId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Dish can't be deleted if it's present in any order of a user or is a part of reviews!");
        }
    }

    public int getComplexityOfExecution(int dishId) throws SQLException {
        String sql = "SELECT complexity_of_execution FROM dishes WHERE id = ?";
        int complexity = 0;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dishId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    complexity = rs.getInt("complexity_of_execution");
                } else {
                    throw new SQLException("Dish with ID " + dishId + " not found.");
                }
            }
        }
        return complexity;
    }

    public Map<Integer, Double> getAverageRatingForEachDish() throws SQLException {
        Map<Integer, Double> averageRatings = new HashMap<>();
        String sql = "SELECT dishId, AVG(rating) AS average_rating FROM reviews GROUP BY dishId";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int dishId = rs.getInt("dishId");
                double avgRating = rs.getDouble("average_rating");
                averageRatings.put(dishId, avgRating);
            }
        }
        return averageRatings;
    }
}
