package repo;

import com.zaxxer.hikari.HikariDataSource;
import model.Dish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReviewRepository {

    private final HikariDataSource dataSource;

    public ReviewRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addNewReview(int dishId, int rating, String comment) throws SQLException {
        // SQL query to insert a new review
        String sql = "INSERT INTO reviews (dishId, rating, comment) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dishId);
            stmt.setInt(2, rating);
            stmt.setString(3, comment);

            stmt.executeUpdate();
        }
    }

    public void deleteAllReviews() throws SQLException {
        String sql = "DELETE FROM reviews";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Execute the DELETE statement
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Log and handle the SQLException appropriately
            throw new SQLException("Error deleting all reviews: " + e.getMessage(), e);
        }
    }
}
