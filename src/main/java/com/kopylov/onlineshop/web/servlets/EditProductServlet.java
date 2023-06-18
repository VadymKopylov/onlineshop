package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.service.ProductService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import com.kopylov.onlineshop.web.util.WebUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class EditProductServlet extends HttpServlet {
    private final ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        Product productById = productService.findById(productId);
        Map<String, Object> productToChange = new HashMap<>();
        productToChange.put("Product", productById);
        response.getWriter().println(PageGenerator.instance().getPage("editProductPage.html", productToChange));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        productService.update(WebUtil.updateProduct(request));
        response.sendRedirect("/admin");
    }
}

