package com.eshoppers.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.enterprise.inject.Produces;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class ConnectionPool {
    private static final ConnectionPool INSTANCE = new ConnectionPool();

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    @Produces
    public DataSource getDataSource() {
        var dbProp = ResourceBundle.getBundle("db");

        var config = new HikariConfig();

        config.setJdbcUrl(dbProp.getString("db.url"));
        config.setUsername(dbProp.getString("db.user"));
        config.setPassword(dbProp.getString("db.password"));
        config.setDriverClassName(dbProp.getString("db.driver"));
        config.setMaximumPoolSize(Integer.parseInt(dbProp.getString("db.max.connections")));

        // performance sensitive settings
        config.setMinimumIdle(0);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(35000);
        config.setMaxLifetime(45000);

        return new HikariDataSource(config);
    }
}
