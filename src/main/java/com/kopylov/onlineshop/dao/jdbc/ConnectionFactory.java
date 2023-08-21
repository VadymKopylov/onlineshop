package com.kopylov.onlineshop.dao.jdbc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public abstract class ConnectionFactory implements DataSource {

    private String url = null;
    private String user = null;
    private String password = null;

    @Override
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
