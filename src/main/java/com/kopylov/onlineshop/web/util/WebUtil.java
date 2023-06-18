package com.kopylov.onlineshop.web.util;

import com.kopylov.onlineshop.entity.Credentials;
import com.kopylov.onlineshop.entity.Product;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class WebUtil {
    public static Product getProduct(HttpServletRequest request) {
        String productName = request.getParameter("productName");
        double productPrice = Double.parseDouble(request.getParameter("price"));
        if (productName == null || productName.isEmpty() || productPrice <= 0) {
            throw new IllegalArgumentException("Invalid product details");
        }
        return Product.builder()
                .name(productName)
                .price(productPrice)
                .build();
    }

    public static Product updateProduct(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String productName = request.getParameter("productName");
        double productPrice = Double.parseDouble(request.getParameter("price"));
        if (id <= 0 || productName == null || productName.isEmpty() || productPrice <= 0) {
            throw new IllegalArgumentException("Invalid product details");
        }
        return Product.builder()
                .id(id)
                .name(productName)
                .price(productPrice)
                .build();
    }

    public static Credentials getCredentials(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Email and password are required");
        }
        return new Credentials(email, password);
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
