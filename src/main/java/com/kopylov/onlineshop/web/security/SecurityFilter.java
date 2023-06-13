package com.kopylov.onlineshop.web.security;

import com.kopylov.onlineshop.service.SecurityService;
import com.kopylov.onlineshop.web.util.WebUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SecurityFilter implements Filter {
    private final SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (securityService.isAuth(WebUtil.getToken(request))) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }
}
