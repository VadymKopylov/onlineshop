package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.back.entity.ProductDto;
import com.kopylov.onlineshop.back.service.CartService;
import com.kopylov.onlineshop.web.util.DefaultSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class DeleteFromCartServlet extends HttpServlet {

    private final CartService cartService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DefaultSession session = (DefaultSession) request.getAttribute("session");
        List<ProductDto> cart = session.getCart();

        cartService.deleteFromCart(cart, request.getParameter("id"));
        response.sendRedirect("/product/cart");
    }
}
