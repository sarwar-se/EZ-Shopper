package com.eshoppers.transaction;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequestScoped
public class ConnectionHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionHolder.class);
    private Connection connection;
    @Inject
    private DataSource dataSource;

    @PostConstruct
    public void createConnection() {
        try {
            this.connection = this.dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.info("Unable to get connection", e);
            throw new RuntimeException("Unable to get connection", e);
        }
    }

    public Connection getConnection() {
        if (connection == null) {
            throw new RuntimeException("Connection is null");
        }

        return connection;
    }

    @PreDestroy
    public void close() {
        LOGGER.info("closing Connection");
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.info("Unable to close connection");
        }
    }
}
