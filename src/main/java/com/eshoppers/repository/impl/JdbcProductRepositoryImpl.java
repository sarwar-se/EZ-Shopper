package com.eshoppers.repository.impl;

import com.eshoppers.annotation.JDBC;
import com.eshoppers.domain.Product;
import com.eshoppers.jdbc.JDBCTemplate;
import com.eshoppers.repository.ProductRepository;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@JDBC
public class JdbcProductRepositoryImpl implements ProductRepository {

    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM product";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product where id = ?";

    private final JDBCTemplate jdbcTemplate;

    @Inject
    public JdbcProductRepositoryImpl(JDBCTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAllProducts() {

        return jdbcTemplate.queryForObject(SELECT_ALL_PRODUCTS, this::extractProducts);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        var products = jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_ID, productId, this::extractProducts);

        return products.size() > 0 ? Optional.of(products.get(0)) : Optional.empty();
    }

    private Product extractProducts(ResultSet resultSet) throws SQLException {
        var product = new Product();

        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setVersion(resultSet.getLong("version"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
        product.setDateLastUpdated(resultSet.getTimestamp("date_last_updated").toLocalDateTime());

        return product;
    }
}
