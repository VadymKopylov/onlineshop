package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.entity.service.ProductService;
import com.kopylov.onlineshop.web.service.SecurityService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import com.kopylov.onlineshop.web.util.WebUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductServlet extends HttpServlet {
    private final ProductService productService;
    private final SecurityService securityService;

    public SearchProductServlet(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (securityService.isAuth(WebUtil.getToken(request))) {
            try {
                int productsCount = 0;
                productsCount = productService.countProduct();
                if (productsCount == 0) {
                    response.getWriter().println(PageGenerator.instance().getPage("emptyDataBasePage.html"));
                } else {
                    response.getWriter().println(PageGenerator.instance().getPage("searchPage.html"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("/user/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (securityService.isAuth(WebUtil.getToken(request))) {
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                List<Product> productById = productService.findProductById(productId);
                Map<String, Object> product = new HashMap<>();
                product.put("Products", productById);
                response.getWriter().println(PageGenerator.instance().getPage("editProductPage.html", product));
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                Map<String, Object> parameters = Map.of("errorMessage", "Product with this ID does not exist");
                response.getWriter().println(PageGenerator.instance().getPage("searchPage.html", parameters));
            }
        } else {
            response.sendRedirect("/user/login");
        }
    }
}
