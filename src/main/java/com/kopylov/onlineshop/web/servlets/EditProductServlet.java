package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.ProductDto;
import com.kopylov.onlineshop.service.ProductService;
import com.kopylov.onlineshop.web.util.PageGenerator;
import com.kopylov.onlineshop.web.util.WebUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class EditProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productId = request.getParameter("id");
        ProductDto productById = productService.findById(productId);
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

