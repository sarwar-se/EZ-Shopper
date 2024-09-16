package com.eshoppers.web;

import com.eshoppers.dto.ProductDTO;
import com.eshoppers.service.CartService;
import com.eshoppers.service.ProductService;
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
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeServlet.class);

    @Inject
    private ProductService productService;

    @Inject
    private CartService cartService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Serving home page");

        final String attribute = req.getParameter("orderSuccess");
        if (Boolean.parseBoolean(attribute)) {
            req.setAttribute("message", "<strong>Congratulations!</strong> You're order has been placed successfully.");
        }

        List<ProductDTO> allProducts = productService.findAllProductSortedByName();
        LOGGER.info("Total product found {}", allProducts.size());

        if (SecurityContext.isAuthenticated(req)) {
            var currentUser = SecurityContext.getCurrentUser(req);
            var cart = cartService.getCartByUser(currentUser);

            req.setAttribute("cart", cart);
        }
        req.setAttribute("products", allProducts);
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }
}
