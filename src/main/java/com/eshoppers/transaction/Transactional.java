package com.eshoppers.transaction;

import java.sql.SQLException;

@FunctionalInterface
public interface Transactional {
    void doInTransaction() throws SQLException;
}
