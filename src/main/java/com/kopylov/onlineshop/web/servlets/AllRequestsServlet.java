package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.service.ProductService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllRequestsServlet extends HttpServlet {
    private final ProductService productService;

    public AllRequestsServlet(ProductService productService) {
        this.productService = productService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> allProducts = productService.findAll(request.getParameter("sort"));
        Map<String, Object> products = new HashMap<>();
        products.put("Products", allProducts);
        response.getWriter().println(PageGenerator.instance().getPage("allProduct.html", products));
    }
}
