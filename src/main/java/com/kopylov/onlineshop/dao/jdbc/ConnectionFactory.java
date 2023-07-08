package com.kopylov.onlineshop.dao.jdbc;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Getter
@Slf4j
public class ConnectionFactory {

    private final String url;
    private final String user;
    private final String password;

    public ConnectionFactory(Properties properties) {
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("login");
        this.password = properties.getProperty("password");
    }

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            log.info("Connection success");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Error with connection to db", e);
        }
    }
}
