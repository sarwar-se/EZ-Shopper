package com.eshoppers.web;

import com.eshoppers.dto.UserDTO;
import com.eshoppers.service.UserService;
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
import java.util.Map;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignupServlet.class);

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Serving signup page");

        req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = copyParametersTo(req);
        Map<String, String> errors = ValidationUtil.getInstance().validate(userDTO);

        if (!errors.isEmpty()) {
            req.setAttribute("userDto", userDTO);
            req.setAttribute("errors", errors);

            LOGGER.warn("User sent invalid data: {}", userDTO);
            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
        } else if (userService.isNotUniqueUsername(userDTO)) {
            errors.put("username", "The username already exists");
            req.setAttribute("userDto", userDTO);
            req.setAttribute("errors", errors);

            LOGGER.warn("Username: {} already exists", userDTO.getUsername());
            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
        } else if (userService.isNotUniqueEmail(userDTO)) { // TODO: not working
            errors.put("email", "The email already exists");
            req.setAttribute("userDto", userDTO);
            req.setAttribute("errors", errors);

            LOGGER.warn("Email: {} already exists", userDTO.getEmail());
            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
        } else {
            LOGGER.info("User is valid, creating a new user with {} ", userDTO);

            userService.saveUser(userDTO);
            resp.sendRedirect("/login");
        }
    }

    private UserDTO copyParametersTo(HttpServletRequest request) {
        var userDTO = new UserDTO();
        userDTO.setUsername(request.getParameter("username"));
        userDTO.setEmail(request.getParameter("email"));
        userDTO.setPassword(request.getParameter("password"));
        userDTO.setPasswordConfirmed(request.getParameter("passwordConfirmed"));
        userDTO.setFirstName(request.getParameter("firstName"));
        userDTO.setLastName(request.getParameter("lastName"));

        return userDTO;
    }
}
