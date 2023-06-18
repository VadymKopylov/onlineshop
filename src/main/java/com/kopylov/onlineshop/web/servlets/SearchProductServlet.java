package com.kopylov.onlineshop.web.servlets;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.service.ProductService;
import com.kopylov.onlineshop.service.SecurityService;
import com.kopylov.onlineshop.web.templater.PageGenerator;
import com.kopylov.onlineshop.web.util.DefaultSession;
import com.kopylov.onlineshop.web.util.WebUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SearchProductServlet extends HttpServlet {
    private final ProductService productService;
    private final SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = WebUtil.getToken(request);
        String searchParameter;
        List<Product> byName = null;
        DefaultSession guestSession = null;
        if (token == null) {
            guestSession = securityService.getGuestSession();

            searchParameter = request.getParameter("search");
            byName = productService.findByName(searchParameter, request.getParameter("sort"));
            guestSession.setAttribute(searchParameter);
        } else {
            searchParameter = request.getParameter("search");
            if (searchParameter != null) {
                byName = productService.findByName(searchParameter, request.getParameter("sort"));
            }
        }

        Map<String, Object> products = new HashMap<>();
        products.put("Products", byName);
        response.getWriter().println(PageGenerator.instance().getPage("allProduct.html", products));
        if (guestSession != null) {
            guestSession.setAttribute(null);
        }
    }
}



