package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.back.entity.ProductDto;
import com.kopylov.onlineshop.back.service.ProductService;
import com.kopylov.onlineshop.web.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class AdminProductEditServlet extends HttpServlet {

    private ProductService productService;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ProductDto> allProducts = productService.findAll(request.getParameter("sort"));
        Map<String, Object> products = new HashMap<>();
        products.put("Products", allProducts);
        response.getWriter().println(PageGenerator.instance().getPage("adminPage.html", products));
    }
}
