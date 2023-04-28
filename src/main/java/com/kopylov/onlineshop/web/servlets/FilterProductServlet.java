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

public class FilterProductServlet extends HttpServlet {
    private final ProductService productService;

    public FilterProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Product> sortedProduct = productService.filter(request.getParameter("sortBy"));
            Map<String, Object> products = new HashMap<>();
            products.put("Products", sortedProduct);
            response.getWriter().println(PageGenerator.instance().getPage("allProduct.html", products));
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error with connection to DataBase", e);
        }
    }
}
