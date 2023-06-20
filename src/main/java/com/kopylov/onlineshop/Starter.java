package com.kopylov.onlineshop;

import com.kopylov.onlineshop.dao.jdbc.ConnectionFactory;
import com.kopylov.onlineshop.dao.jdbc.JdbcProductsDao;
import com.kopylov.onlineshop.dao.jdbc.JdbcUserDao;
import com.kopylov.onlineshop.service.CartService;
import com.kopylov.onlineshop.service.ProductService;
import com.kopylov.onlineshop.service.UserService;
import com.kopylov.onlineshop.util.PropertiesReader;
import com.kopylov.onlineshop.web.security.AdminSecurityFilter;
import com.kopylov.onlineshop.web.security.UserSecurityFilter;
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

    private static final int SERVER_PORT = 8081;
    private static final String PROPERTIES = "application.properties";
    private static final String SESSION_TIME_KEY = "sessionTimeToLive";

    public static void main(String[] args) throws Exception {
        PropertiesReader propertiesReader = new PropertiesReader(PROPERTIES);
        Properties properties = propertiesReader.getProperties();

        ConnectionFactory connectionFactory = new ConnectionFactory(properties);
        JdbcProductsDao jdbcProductsDao = new JdbcProductsDao(connectionFactory);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(connectionFactory);
        UserService userService = new UserService(jdbcUserDao);

        long sessionTimeToLive = propertiesReader.getProperties(SESSION_TIME_KEY);
        SecurityService securityService = new SecurityService(userService, sessionTimeToLive);

        ProductService productService = new ProductService(jdbcProductsDao);
        CartService cartService = new CartService(productService);

        Server server = new Server(SERVER_PORT);
        server.setHandler(createContextServletHandler(securityService, productService, cartService));

        server.start();
    }

    private static ServletContextHandler createContextServletHandler(
            SecurityService securityService, ProductService productService, CartService cartService) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        addServlets(securityService, productService, cartService, context);
        addFilters(securityService, context);

        return context;
    }

    private static void addServlets(SecurityService securityService, ProductService productService,
                                    CartService cartService, ServletContextHandler context) {

        context.addServlet(new ServletHolder(new AllRequestsServlet(productService)), "");
        context.addServlet(new ServletHolder(new AdminProductEditServlet(productService)), "/admin");
        context.addServlet(new ServletHolder(new AddProductServlet(productService)), "/admin/product/add");
        context.addServlet(new ServletHolder(new EditProductServlet(productService)), "/admin/product/edit");
        context.addServlet(new ServletHolder(new DeleteProductServlet(productService)), "/admin/product/delete");
        context.addServlet(new ServletHolder(new LoginServlet(securityService)), "/login");
        context.addServlet(new ServletHolder(new LogoutServlet(securityService)), "/logout");
        context.addServlet(new ServletHolder(new SearchProductServlet(productService, securityService)), "/search");
        context.addServlet(new ServletHolder(new CartServlet()), "/product/cart");
        context.addServlet(new ServletHolder(new AddToCartServlet(cartService)), "/product/cart/add");
        context.addServlet(new ServletHolder(new DeleteFromCartServlet(cartService)), "/product/cart/delete");
    }

    private static void addFilters(SecurityService securityService, ServletContextHandler context) {

        context.addFilter(new FilterHolder(new UserSecurityFilter(securityService)),
                "/product/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(new AdminSecurityFilter(securityService)),
                "/admin", EnumSet.of(DispatcherType.REQUEST));
    }
}
