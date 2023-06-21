package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.back.service.SecurityService;
import com.kopylov.onlineshop.web.util.PageGenerator;
import com.kopylov.onlineshop.web.util.DefaultSession;
import com.kopylov.onlineshop.web.util.WebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginServlet extends HttpServlet {
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write(PageGenerator.instance().getPage("login.html"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DefaultSession session = securityService.login(WebUtil.getCredentials(request));
        if (session != null) {
            Cookie cookie = new Cookie("sessionId", session.getToken());
            cookie.setPath("/");
            response.addCookie(cookie);
            if (session.getUser().getRole().toString().equals("ADMIN")) {
                response.sendRedirect("/admin");
            } else if (session.getUser().getRole().toString().equals("USER")) {
                response.sendRedirect("");
            }
        } else {
            response.sendRedirect("/login?error=invalidCredentials");
        }
    }
}
