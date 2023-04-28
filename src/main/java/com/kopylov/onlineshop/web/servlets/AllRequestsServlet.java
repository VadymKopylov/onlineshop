package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.entity.service.ProductService;
import com.kopylov.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllRequestsServlet extends HttpServlet {
    private final ProductService productService;

    public AllRequestsServlet(ProductService productService) {
        this.productService = productService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Product> allProducts = productService.findAll();
            Map<String, Object> products = new HashMap<>();
            products.put("Products", allProducts);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(PageGenerator.instance().getPage("allProduct.html", products));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error with connection to DataBase", e);
        }
    }
}
