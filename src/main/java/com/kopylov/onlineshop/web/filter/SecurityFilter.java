package com.kopylov.onlineshop.web.filter;

import com.kopylov.onlineshop.web.service.SecurityService;
import com.kopylov.onlineshop.web.util.WebUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {
    private final SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (securityService.isAuth(WebUtil.getToken(request))) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect("/user/login");
        }
    }

    @Override
    public void destroy() {

    }
}
