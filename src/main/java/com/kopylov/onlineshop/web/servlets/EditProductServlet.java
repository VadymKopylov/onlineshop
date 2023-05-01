package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.service.ProductService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import com.kopylov.onlineshop.web.util.WebUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditProductServlet extends HttpServlet {
    private ProductService productService;


    public EditProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            productService.updateProduct(WebUtil.getProductById(request, id));
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(PageGenerator.instance().getPage("afterAddingPage.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

