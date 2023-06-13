package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.service.SecurityService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import com.kopylov.onlineshop.web.util.WebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserLoginServlet extends HttpServlet {
    private final SecurityService securityService;

    public UserLoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write(PageGenerator.instance().getPage("login.html"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userToken = securityService.login(WebUtil.getUser(request));
        if (userToken != null) {
            Cookie cookie = new Cookie("user-token", userToken);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect("");
        } else {
            response.getWriter().println(PageGenerator.instance().getPage("login.html"));
        }
    }
}
