package com.eshoppers.service;

import com.eshoppers.domain.Cart;
import com.eshoppers.domain.User;

public interface CartService {
    Cart getCartByUser(User currentUser);

    void addProductToCart(String productId, Cart cart);

    void removeProductToCart(String productId, Cart cart);

    void removeCartItemToCart(String productId, Cart cart);
}
