package com.eshoppers.repository.impl;

import com.eshoppers.domain.Order;
import com.eshoppers.domain.User;
import com.eshoppers.jdbc.JDBCTemplate;
import com.eshoppers.repository.CartRepository;
import com.eshoppers.repository.OrderRepository;
import com.eshoppers.repository.ShippingAddressRepository;

import java.util.HashSet;
import java.util.Set;

public class JdbcOrderRepositoryImpl implements OrderRepository {
    private JDBCTemplate jdbcTemplate = new JDBCTemplate();
    private CartRepository cartRepository = new JdbcCartRepositoryImpl();
    private ShippingAddressRepository shippingAddressRepository = new JdbcShippingAddressRepositoryImpl();

    public static final String FIND_ORDER_BY_USER = """
            SELECT
                id,
                shipping_address_id,
                cart_id,
                version,
                date_created,
                date_last_updated,
                shipping_date,
                shipped,
                user_id
            FROM `order` WHERE user_id = ?
            """;

    public static final String INSERT_ORDER = """
            INSERT INTO `order` (
                shipping_address_id,
                cart_id,
                version,
                shipped,
                user_id,
                shipping_date,
                date_created,
                date_last_updated)
            VALUES (?,?,?,?,?,?,?,?)
            """;

    @Override
    public Order save(Order order) {
        var id = jdbcTemplate.executeInsertQuery(INSERT_ORDER,
                order.getShippingAddress().getId(),
                order.getCart().getId(),
                0L,
                order.isShipped(),
                order.getUser().getId(),
                order.getShippingDate(),
                order.getDateCreated(),
                order.getDateLastUpdated()
        );
        order.setId(id);

        return order;
    }

    @Override
    public Set<Order> findOrderByUser(User user) {
        var orders = jdbcTemplate.queryForObject(FIND_ORDER_BY_USER,
                user.getId(), resultSet -> {
                    var order = new Order();

                    order.setId(resultSet.getLong("id"));
                    order.setVersion(resultSet.getLong("version"));
                    order.setDateCreated(resultSet.getTimestamp("date_created").toLocalDateTime());
                    order.setDateLastUpdated(resultSet.getTimestamp("date_last_updated").toLocalDateTime());
                    order.setShipped(resultSet.getBoolean("shipped"));
                    order.setShippingDate(resultSet.getTimestamp("shipping_date") != null ?
                            resultSet.getTimestamp("shipping_date").toLocalDateTime().toLocalDate() : null);

                    cartRepository.findById(resultSet.getLong("cart_id"))
                            .ifPresent(order::setCart);

                    shippingAddressRepository.findById(resultSet.getLong("shipping_address_id"))
                            .ifPresent(order::setShippingAddress);
                    order.setUser(user);

                    return order;
                });

        return new HashSet<>(orders);
    }
}
