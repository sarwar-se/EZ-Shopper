package com.eshoppers.repository;

import com.eshoppers.domain.Order;
import com.eshoppers.domain.User;

import java.util.Set;

public interface OrderRepository {
    Order save(Order order);

    Set<Order> findOrderByUser(User user);
}
