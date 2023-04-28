package com.kopylov.onlineshop.database.mapper;

import com.kopylov.onlineshop.entity.Product;
import org.apache.commons.text.StringEscapeUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = StringEscapeUtils.escapeHtml4(resultSet.getString("name"));
        double price = resultSet.getDouble("price");
        Timestamp creationDate = resultSet.getTimestamp("creationDate");
        return new Product(id, name, price, creationDate.toLocalDateTime());
    }

    public Product mapRow(ResultSet resultSet, int id) throws SQLException {
        String name = StringEscapeUtils.escapeHtml4(resultSet.getString("name"));
        double price = resultSet.getDouble("price");
        Timestamp creationDate = resultSet.getTimestamp("creationDate");
        return new Product(id, name, price, creationDate.toLocalDateTime());
    }
}
