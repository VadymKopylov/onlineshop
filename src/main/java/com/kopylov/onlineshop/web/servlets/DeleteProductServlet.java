package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    private final ProductService productService;

    public DeleteProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            int productId = Integer.parseInt(request.getParameter("id"));
            productService.deleteById(productId);
            response.sendRedirect("/");
    }
}
