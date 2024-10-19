package com.eshoppers.web;

import com.eshoppers.dto.ShippingAddressDTO;
import com.eshoppers.service.CartService;
import com.eshoppers.service.OrderService;
import com.eshoppers.security.SecurityContext;
import com.eshoppers.util.ValidationUtil;
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

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServlet.class);

    @Inject
    private CartService cartService;

    @Inject
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Serving order page");
        addCartToUi(req);
        req.setAttribute("countries", getCountries());

        req.getRequestDispatcher("/WEB-INF/order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Handle order request form");

        var shippingAddress = copyParametersTo(req);

        var errors = ValidationUtil.getInstance().validate(shippingAddress);

        if (!errors.isEmpty()) {
            req.setAttribute("countries", getCountries());
            req.setAttribute("errors", errors);
            req.setAttribute("shippingAddress", shippingAddress);
            addCartToUi(req);
            req.getRequestDispatcher("/WEB-INF/order.jsp").forward(req, resp);
        } else {
            orderService.processOrder(shippingAddress, SecurityContext.getCurrentUser(req));
            resp.sendRedirect("/home?orderSuccess=true");
        }
    }

    private void addCartToUi(HttpServletRequest req) {
        if (SecurityContext.isAuthenticated(req)) {
            var currentUser = SecurityContext.getCurrentUser(req);
            var cart = cartService.getCartByUser(currentUser);
            req.setAttribute("cart", cart);
        }
    }

    private ShippingAddressDTO copyParametersTo(HttpServletRequest req) {
        var shippingAddress = new ShippingAddressDTO();

        shippingAddress.setAddress(req.getParameter("address"));
        shippingAddress.setAddress2(req.getParameter("address2"));
        shippingAddress.setState(req.getParameter("state"));
        shippingAddress.setCountry(req.getParameter("country"));
        shippingAddress.setZip(req.getParameter("zip"));
        shippingAddress.setMobileNumber(req.getParameter("mobileNumber"));

        return shippingAddress;
    }

    private List<String> getCountries() {
        return List.of("Bangladesh", "USA", "Canada", "Japan");
    }
}
