package com.kopylov.onlineshop.web.security;

import com.kopylov.onlineshop.service.SecurityService;
import com.kopylov.onlineshop.web.util.DefaultSession;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class UserSecurityFilter implements Filter {
    private final SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionId")) {
                    DefaultSession session = securityService.getSession(cookie.getValue());
                    if (session != null) {
                        request.setAttribute("session",session);
                        filterChain.doFilter(request, response);
                    } else {
                        response.sendRedirect("/login");
                    }
                }
            }
        }
    }
}

