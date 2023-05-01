package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.service.UserService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import com.kopylov.onlineshop.web.util.WebUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UserLoginServlet extends HttpServlet {
    private final UserService userService;

    public UserLoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(pageGenerator.getPage("login.html"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String userToken = userService.login(WebUtil.getUser(request));
            if (userToken != null) {
                Cookie cookie = new Cookie("user-token", userToken);
                cookie.setPath("/");
                response.addCookie(cookie);
                response.sendRedirect("/index.html");
            } else {
                Map<String, Object> parameters = Map.of("errorMessage", "Wrong password!");
                response.getWriter().println(PageGenerator.instance().getPage("login.html", parameters));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
