package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.service.CartService;
import com.kopylov.onlineshop.web.util.DefaultSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class AddToCartServlet extends HttpServlet {
    private final CartService cartService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DefaultSession session = (DefaultSession) request.getAttribute("session");
        List<Product> cart = session.getCart();

        cartService.addToCart(cart, Integer.parseInt(request.getParameter("id")));
        response.sendRedirect("/");
    }
}
