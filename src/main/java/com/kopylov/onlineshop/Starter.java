package com.kopylov.onlineshop;

import com.kopylov.onlineshop.dao.jdbc.ConnectionFactory;
import com.kopylov.onlineshop.dao.jdbc.JdbcProductsDao;
import com.kopylov.onlineshop.dao.jdbc.JdbcUserDao;
import com.kopylov.onlineshop.service.ProductService;
import com.kopylov.onlineshop.service.UserService;
import com.kopylov.onlineshop.util.PropertiesReader;
import com.kopylov.onlineshop.web.security.SecurityFilter;
import com.kopylov.onlineshop.service.SecurityService;
import com.kopylov.onlineshop.web.servlets.*;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.EnumSet;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {
        PropertiesReader propertiesReader = new PropertiesReader("application.properties");
        Properties properties = propertiesReader.getProperties();
        ConnectionFactory connectionFactory = new ConnectionFactory(properties);
        JdbcProductsDao jdbcProductsDao = new JdbcProductsDao(connectionFactory);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(connectionFactory);
        UserService userService = new UserService(jdbcUserDao);
        SecurityService securityService = new SecurityService(userService);
        ProductService productService = new ProductService(jdbcProductsDao);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new AllRequestsServlet(productService)), "");
        context.addServlet(new ServletHolder(new AddProductServlet(productService)), "/product/add");
        context.addServlet(new ServletHolder(new EditProductServlet(productService)), "/product/edit");
        context.addServlet(new ServletHolder(new DeleteProductServlet(productService)), "/product/delete");
        context.addServlet(new ServletHolder(new UserLoginServlet(securityService)), "/login");
        context.addServlet(new ServletHolder(new SearchProductServlet(productService)),"/search");
        context.addFilter(new FilterHolder(new SecurityFilter(securityService)), "/product/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8081);
        server.setHandler(context);

        server.start();
    }
}
