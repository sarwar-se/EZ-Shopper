package com.eshoppers.repository;

import com.eshoppers.domain.CartItem;

public interface CartItemRepository {
    CartItem save(CartItem cartItem);

    CartItem update(CartItem cartItem);

    void remove(CartItem cartItem);
}
