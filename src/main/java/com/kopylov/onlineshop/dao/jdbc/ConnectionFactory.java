package com.kopylov.onlineshop.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private String url;
    private String user;
    private String password;

    public ConnectionFactory(Properties properties){
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("login");
        this.password = properties.getProperty("password");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error with connection to db", e);
        }
    }
}
