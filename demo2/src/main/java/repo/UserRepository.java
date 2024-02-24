package repo;

import com.zaxxer.hikari.HikariDataSource;
import model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepository {

    private final HikariDataSource dataSource;

    public UserRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean doesUserExist(String login) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE login = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void createUser(String name, String login, String password) throws SQLException {
        String sql = "INSERT INTO users (username, login, password) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, login);
            stmt.setString(3, password);
            stmt.executeUpdate();
        }
        // Resources are automatically closed by try-with-resources statement
    }

    public boolean isUserAuthenticated(HttpServletRequest request, String login, String password) throws SQLException {
        boolean isAuthenticated = false;
        String sql = "SELECT password FROM users WHERE login = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    if (storedPassword != null && storedPassword.equals(password)) {
                        isAuthenticated = true;
                    } else {
                        request.setAttribute("loginError", "Invalid login or password.");
                    }
                } else {
                    request.setAttribute("loginError", "User with such login does not exist.");
                }
            }
        }
        return isAuthenticated;
    }

    public Integer getUserId(String login, String password) throws SQLException {
        String sql = "SELECT id FROM users WHERE login = ? AND password = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, password);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return null; // Return null if the user is not found or in case of an exception
    }

}
