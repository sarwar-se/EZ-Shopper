package com.eshoppers.repository.impl;

import com.eshoppers.annotation.Local;
import com.eshoppers.domain.CartItem;
import com.eshoppers.repository.CartItemRepository;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Local
public class CartItemRepositoryImpl implements CartItemRepository {
    private static final Set<CartItem> CARTS = new CopyOnWriteArraySet<>();

    @Override
    public CartItem save(CartItem cartItem) {
        CARTS.add(cartItem);
        return cartItem;
    }

    @Override
    public CartItem update(CartItem cartItem) {
        CARTS.add(cartItem);
        return cartItem;
    }

    @Override
    public void remove(CartItem cartItem) {
        CARTS.remove(cartItem);
    }
}
