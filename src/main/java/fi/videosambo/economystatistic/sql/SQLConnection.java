package fi.videosambo.economystatistic.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLConnection {

    private HikariConfig dbConfig;
    private HikariDataSource dbDataSource;

    public SQLConnection() {
        dbConfig = new HikariConfig();
        dbConfig.setJdbcUrl("jdbc:mysql://localhost:3306/economystatistics");
        dbConfig.setUsername("user");
        dbConfig.setPassword("password");
        dbConfig.addDataSourceProperty("cachePrepStmts", "true");
        dbConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        dbConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dbDataSource = new HikariDataSource(dbConfig);
    }

    public Connection getConnection() {
        try {
            return dbDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
