package com.kopylov.onlineshop;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.kopylov.ioc.context.ApplicationContext;
import com.kopylov.ioc.context.ClassPathApplicationContext;
import com.kopylov.onlineshop.back.service.SecurityService;
import com.kopylov.onlineshop.back.util.PropertiesReader;
import com.kopylov.onlineshop.web.security.AdminSecurityFilter;
import com.kopylov.onlineshop.web.security.UserSecurityFilter;
import com.kopylov.onlineshop.web.servlets.*;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class Starter {

    private static final int SERVER_PORT = 8081;
    private static final String PROPERTIES = "application.properties";
    private static final String SESSION_TIME_KEY = "sessionTimeToLive";

    public static void main(String[] args) throws Exception {
        PropertiesReader propertiesReader = new PropertiesReader(PROPERTIES);
        ApplicationContext applicationContext = new ClassPathApplicationContext("/context/root-context.xml");

        SecurityService securityService = (SecurityService) applicationContext.getBean("securityService");
        long sessionTimeToLive = propertiesReader.getProperties(SESSION_TIME_KEY);
        securityService.setSessionTimeToLive(sessionTimeToLive);
        Server server = new Server(SERVER_PORT);
        server.setHandler(createContextServletHandler(applicationContext));

        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);

        server.start();
    }

    private static ServletContextHandler createContextServletHandler(ApplicationContext applicationContext) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        addServlets(applicationContext, context);
        addFilters(applicationContext, context);

        return context;
    }

    private static void addServlets(ApplicationContext applicationContext, ServletContextHandler context) {
        context.addServlet(new ServletHolder(applicationContext.getBean(AllRequestsServlet.class)), "");
        context.addServlet(new ServletHolder(applicationContext.getBean(AdminProductEditServlet.class)), "/admin");
        context.addServlet(new ServletHolder(applicationContext.getBean(AddProductServlet.class)), "/admin/product/add");
        context.addServlet(new ServletHolder(applicationContext.getBean(EditProductServlet.class)), "/admin/product/edit");
        context.addServlet(new ServletHolder(applicationContext.getBean(DeleteProductServlet.class)), "/admin/product/delete");
        context.addServlet(new ServletHolder(applicationContext.getBean(LoginServlet.class)), "/login");
        context.addServlet(new ServletHolder(applicationContext.getBean(LogoutServlet.class)), "/logout");
        context.addServlet(new ServletHolder(applicationContext.getBean(SearchProductServlet.class)), "/search");
        context.addServlet(new ServletHolder(applicationContext.getBean(CartServlet.class)), "/product/cart");
        context.addServlet(new ServletHolder(applicationContext.getBean(AddToCartServlet.class)), "/product/cart/add");
        context.addServlet(new ServletHolder(applicationContext.getBean(DeleteFromCartServlet.class)), "/product/cart/delete");
    }

    private static void addFilters(ApplicationContext applicationContext, ServletContextHandler context) {

        context.addFilter(new FilterHolder(applicationContext.getBean(UserSecurityFilter.class)),
                "/product/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(applicationContext.getBean(AdminSecurityFilter.class)),
                "/admin/*", EnumSet.of(DispatcherType.REQUEST));
    }
}
