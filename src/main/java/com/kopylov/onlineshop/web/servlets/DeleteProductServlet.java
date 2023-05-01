package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.service.ProductService;
import com.kopylov.onlineshop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class DeleteProductServlet extends HttpServlet {
    private final ProductService productService;

    public DeleteProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        int productsCount;
        try {
            productsCount = productService.countProduct();
            if (productsCount == 0) {
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(PageGenerator.instance().getPage("emptyDataBasePage.html"));
            } else if (request.getParameter("action").equals("deleteProductPage")) {
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(PageGenerator.instance().getPage("deleteProductPage.html"));
            } else {
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(PageGenerator.instance().getPage("afterDeletingPage.html"));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("productId"));
            productService.deleteProductById(id);
            response.getWriter().println(PageGenerator.instance().getPage("afterDeletingPage.html"));
        } catch (Exception e) {
            Map<String, Object> parameters = Map.of("errorMessage", "Product with this ID does not exist");
            response.getWriter().println(PageGenerator.instance().getPage("deleteProductPage.html", parameters));
        }
    }
}
