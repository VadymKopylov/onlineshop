package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.back.entity.Product;
import com.kopylov.onlineshop.web.util.PageGenerator;
import com.kopylov.onlineshop.web.util.DefaultSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DefaultSession session = (DefaultSession) request.getAttribute("session");
        List<Product> cart = session.getCart();

        Map<String, Object> products = new HashMap<>();
        products.put("Products", cart);
        response.getWriter().println(PageGenerator.instance().getPage("cartPage.html", products));
    }
}