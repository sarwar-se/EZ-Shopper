package com.eshoppers.web;

import com.eshoppers.service.CartService;
import com.eshoppers.security.SecurityContext;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutServlet.class);

    @Inject
    private CartService cartService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Serving checkout page");

        var currentUser = SecurityContext.getCurrentUser(req);
        var cart = cartService.getCartByUser(currentUser);
        req.setAttribute("cart", cart);

        req.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(req, resp);
    }
}
