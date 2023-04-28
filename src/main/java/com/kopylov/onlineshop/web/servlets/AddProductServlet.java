package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.service.ProductService;
import com.kopylov.onlineshop.web.service.SecurityService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import com.kopylov.onlineshop.web.util.WebUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    private final ProductService productService;
    private final SecurityService securityService;

    public AddProductServlet(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (securityService.isAuth(WebUtil.getToken(request))) {
            response.getWriter().println(PageGenerator.instance().getPage("addProduct.html"));
        } else {
            response.sendRedirect("/user/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (securityService.isAuth(WebUtil.getToken(request))) {
            try {
                productService.addToDataBase(WebUtil.getProduct(request));
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(PageGenerator.instance().getPage("afterAddingPage.html"));
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.sendRedirect("/user/login");
        }
    }
}
