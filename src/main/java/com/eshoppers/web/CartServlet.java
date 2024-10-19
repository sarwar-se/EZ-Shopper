package com.eshoppers.web;

import com.eshoppers.domain.Cart;
import com.eshoppers.domain.User;
import com.eshoppers.enums.ActionType;
import com.eshoppers.service.CartService;
import com.eshoppers.security.SecurityContext;
import com.eshoppers.util.StringUtil;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/add-to-cart")
public class CartServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartServlet.class);

    @Inject
    private CartService cartService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var productId = req.getParameter("productId");
        var action = req.getParameter("action");
        var cart = getCart(req);

        if (StringUtil.isNotEmpty(action)) {
            processCart(productId, action, cart);
            resp.sendRedirect("/checkout");
            return;
        }

        LOGGER.info("Received request to add product with id: {} to cart", productId);

        cartService.addProductToCart(productId, cart);
        resp.sendRedirect("/home");
    }

    private Cart getCart(HttpServletRequest req) {
        final User currentUser = SecurityContext.getCurrentUser(req);

        return cartService.getCartByUser(currentUser);
    }

    private void processCart(String productId, String action, Cart cart) {
        switch (ActionType.valueOf(action.toUpperCase())) {
            case ADD -> {
                LOGGER.info("Received request to add product with id: {} to cart", productId);
                cartService.addProductToCart(productId, cart);
            }
            case REMOVE -> {
                LOGGER.info("Received request to remove product with id: {} to cart", productId);
                cartService.removeProductToCart(productId, cart);
            }
            case REMOVE_CART_ITEM -> {
                LOGGER.info("Received request to remove product cart item with id: {} to cart", productId);
                cartService.removeCartItemToCart(productId, cart);
            }
        }
    }
}
