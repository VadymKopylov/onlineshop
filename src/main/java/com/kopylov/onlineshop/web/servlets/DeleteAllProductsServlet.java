package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.service.ProductService;
import com.kopylov.onlineshop.web.service.SecurityService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import com.kopylov.onlineshop.web.util.WebUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAllProductsServlet extends HttpServlet {
    private final ProductService productService;
    private final SecurityService securityService;

    public DeleteAllProductsServlet(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (securityService.isAuth(WebUtil.getToken(request))) {
            try {
                productService.deleteAllProducts();
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(PageGenerator.instance().getPage("afterDeletingAllPage.html"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("/user/login");
        }
    }
}
