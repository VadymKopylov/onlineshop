package com.kopylov.onlineshop;

import com.kopylov.onlineshop.database.productsdao.JdbcProductsDao;
import com.kopylov.onlineshop.database.userdao.JdbcUserDao;
import com.kopylov.onlineshop.entity.service.ProductService;
import com.kopylov.onlineshop.entity.service.UserService;
import com.kopylov.onlineshop.web.service.SecurityService;
import com.kopylov.onlineshop.web.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {
        JdbcProductsDao jdbcProductsDao = new JdbcProductsDao();
        JdbcUserDao jdbcUserDao = new JdbcUserDao();
        SecurityService securityService = new SecurityService();
        ProductService productService = new ProductService(jdbcProductsDao);
        UserService userService = new UserService(jdbcUserDao, securityService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new AllRequestsServlet(productService)), "/index.html");
        context.addServlet(new ServletHolder(new AddProductServlet(productService, securityService)), "/product/add");
        context.addServlet(new ServletHolder(new SearchProductServlet(productService, securityService)), "/product/search");
        context.addServlet(new ServletHolder(new EditProductServlet(productService, securityService)), "/product/edit");
        context.addServlet(new ServletHolder(new DeleteProductServlet(productService, securityService)), "/product/delete");
        context.addServlet(new ServletHolder(new DeleteAllProductsServlet(productService, securityService)), "/product/delete/all");
        context.addServlet(new ServletHolder(new FilterProductServlet(productService)), "/product/filter");
        context.addServlet(new ServletHolder(new UserLoginServlet(userService)), "/user/login");

        Server server = new Server(8081);
        server.setHandler(context);

        server.start();
    }
}
