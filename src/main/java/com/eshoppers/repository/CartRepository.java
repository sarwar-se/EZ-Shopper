package com.eshoppers.repository;

import com.eshoppers.domain.Cart;
import com.eshoppers.domain.User;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUser(User currentUser);

    Cart save(Cart cart);

    Cart update(Cart cart);

    Optional<Cart> findById(long cartId);
}
