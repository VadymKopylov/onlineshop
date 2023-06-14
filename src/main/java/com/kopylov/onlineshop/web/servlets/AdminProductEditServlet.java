package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.service.ProductService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class AdminProductEditServlet extends HttpServlet {
    private final ProductService productService;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> allProducts = productService.findAll(request.getParameter("sort"));
        Map<String, Object> products = new HashMap<>();
        products.put("Products", allProducts);
        response.getWriter().println(PageGenerator.instance().getPage("adminPage.html", products));
    }
}
