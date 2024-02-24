package connection.pool;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataConnectionPool {
    private static HikariDataSource dataSource;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to find the PostgreSQL JDBC Driver class", e);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost/Restaurant");
        config.setUsername("postgres");
        config.setPassword("9639");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
