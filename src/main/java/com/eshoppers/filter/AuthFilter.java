package com.eshoppers.filter;

import com.eshoppers.util.SecurityContext;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Stream;

@WebFilter( urlPatterns = "/*")
public class AuthFilter implements Filter {
    private static final String[] ALLOWED_CONTENTS = {".css", ".js", ".jpg", "home", "login", "signup"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) request;
        var requestedUri = httpServletRequest.getRequestURI();

        boolean allowed = Stream.of(ALLOWED_CONTENTS).anyMatch(requestedUri::contains);
        if (requestedUri.equals("/") || allowed || SecurityContext.isAuthenticated(httpServletRequest)) {
            filterChain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("/login");
        }
    }
}
