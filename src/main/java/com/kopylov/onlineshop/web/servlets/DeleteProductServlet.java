package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class DeleteProductServlet extends HttpServlet {
    private final ProductService productService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        productService.deleteById(productId);
        response.sendRedirect("/admin");
    }
}
