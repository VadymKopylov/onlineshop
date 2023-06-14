package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.service.SecurityService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class LogoutServlet extends HttpServlet {
    private final SecurityService securityService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionId")) {
                    securityService.logout(cookie.getName());
                }
            }
        }
        response.sendRedirect("");
    }
}
