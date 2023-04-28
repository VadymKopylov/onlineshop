package com.kopylov.onlineshop.web.util;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class WebUtil {
    public static Product getProduct(HttpServletRequest request) {
        String productName = request.getParameter("productName");
        double productPrice = Double.parseDouble(request.getParameter("price"));
        return new Product(productName, productPrice);
    }

    public static Product getProductById(HttpServletRequest request, int id) {
        String productName = request.getParameter("productName");
        double productPrice = Double.parseDouble(request.getParameter("price"));
        return new Product(id, productName, productPrice);
    }

    public static User getUser(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        return new User(email, password);
    }

    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }
}
