package com.eshoppers.transaction;

import java.sql.SQLException;

@FunctionalInterface
public interface CallableTransactional<T> {
    T doInTransaction() throws SQLException;
}
