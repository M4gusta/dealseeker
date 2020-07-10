package com.magusta.dealseeker.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static final String url = "jdbc:mariadb://localhost/dealseeker";
    private static final String username = "magusta";
    private static final String password = "";

    public static Connection getConnection() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        HikariDataSource ds = new HikariDataSource(config);

        return ds.getConnection();
    }
}
