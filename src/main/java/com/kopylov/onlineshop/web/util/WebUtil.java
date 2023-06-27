package com.kopylov.onlineshop.web.util;

import com.kopylov.onlineshop.back.entity.Credentials;
import com.kopylov.onlineshop.back.entity.Product;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class WebUtil {

    public static Product getProduct(HttpServletRequest request) {
        String productName = request.getParameter("productName");
        String productPrice = request.getParameter("price");

        Validator.validateProductDetails(productName, productPrice);

        return new Product()
                .setName(productName)
                .setPrice(Double.parseDouble(productPrice));
    }

    public static Product updateProduct(HttpServletRequest request) {
        String id = request.getParameter("id");
        String productName = request.getParameter("productName");
        String productPrice = request.getParameter("price");

        Validator.validateProductUpdate(id, productName, productPrice);

        return new Product()
                .setId(id)
                .setName(productName)
                .setPrice(Double.parseDouble(productPrice));
    }

    public static Credentials getCredentials(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Validator.validateCredentials(email, password);

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
