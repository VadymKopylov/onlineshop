package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.service.ProductService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductServlet extends HttpServlet {
    private final ProductService productService;

    public SearchProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        List<Product> byName;

        String searchAttribute = (String) session.getAttribute("search");
        if (searchAttribute != null) {
            byName = productService.findByName(searchAttribute, request.getParameter("sort"));
        } else {
            searchAttribute = request.getParameter("search");
            byName = productService.findByName(searchAttribute, request.getParameter("sort"));
            session.setAttribute("search", searchAttribute);
        }

        Map<String, Object> products = new HashMap<>();
        products.put("Products", byName);
        response.getWriter().println(PageGenerator.instance().getPage("allProduct.html", products));
    }
}


