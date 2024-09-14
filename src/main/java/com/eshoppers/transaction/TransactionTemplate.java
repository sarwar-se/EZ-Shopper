package com.eshoppers.transaction;

import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionTemplate {
    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionTemplate.class);

    @Inject
    private ConnectionHolder connectionHolder;

    public void execute(Transactional transactional) {
        LOGGER.info("Executing transactions");
        var connection = connectionHolder.getConnection();

        try {
            setAutoCommitFalse(connection);
            transactional.doInTransaction();
            connection.commit();
        } catch (Exception e) {
            rollback(connection, e);
        } finally {
            setAutoCommitTrue(connection);
        }
    }

    public <T> T execute(CallableTransactional<T> callableTransactional) {
        LOGGER.info("Executing transactions");
        var connection = connectionHolder.getConnection();

        try {
            setAutoCommitFalse(connection);
            T result = callableTransactional.doInTransaction();
            connection.commit();

            return result;
        } catch (Exception e) {
            rollback(connection, e);
            throw new RuntimeException(e);
        } finally {
            setAutoCommitTrue(connection);
        }
    }

    private void setAutoCommitFalse(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }

    private void setAutoCommitTrue(Connection conn) {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.info("Unable to set auto commit");
            throw new RuntimeException(e);
        }
    }

    private void rollback(Connection conn, Throwable throwable) {
        LOGGER.error("Rolling back on application exception from transaction callback", throwable);
        try {
            conn.rollback();
        } catch (SQLException e) {
            LOGGER.error("Unable to rollback", e);
        }
    }

}
