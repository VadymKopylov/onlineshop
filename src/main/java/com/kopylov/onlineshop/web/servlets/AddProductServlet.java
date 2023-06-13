package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.service.ProductService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import com.kopylov.onlineshop.web.util.WebUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    private final ProductService productService;

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(PageGenerator.instance().getPage("addProduct.html"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        productService.addToDataBase(WebUtil.getProduct(request));
        request.setAttribute("message", "Product was added");
        response.sendRedirect("/?message=success");
    }
}
