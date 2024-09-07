package com.eshoppers.web;

import com.eshoppers.domain.User;
import com.eshoppers.dto.LoginDTO;
import com.eshoppers.exceptions.UserNotFoundException;
import com.eshoppers.repository.impl.UserRepositoryImpl;
import com.eshoppers.service.UserService;
import com.eshoppers.service.impl.UserServiceImpl;
import com.eshoppers.util.SecurityContext;
import com.eshoppers.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Serving login page");

        String logout = req.getParameter("logout");

        if (Boolean.parseBoolean(logout)) {
            req.setAttribute("message", "You have been successfully logged out.");
        }

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var loginDTO = new LoginDTO(req.getParameter("username"), req.getParameter("password"));
        LOGGER.info("Receiving login data: {}", loginDTO);

        var errors = ValidationUtil.getInstance().validate(loginDTO);

        if (!errors.isEmpty()) {
            LOGGER.warn("Failed to login, sending login form again");

            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }

        try {
            login(loginDTO, req);
            LOGGER.info("Login successful, redirecting to home page");
            resp.sendRedirect("/home");
        } catch (UserNotFoundException e) {
            LOGGER.error("Incorrect username/password", e);
            errors.put("username", "Incorrect username/password");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }

    private void login(LoginDTO loginDTO, HttpServletRequest req) throws UserNotFoundException {
        User user = userService.verifyUser(loginDTO);

        SecurityContext.login(req, user);
    }
}
