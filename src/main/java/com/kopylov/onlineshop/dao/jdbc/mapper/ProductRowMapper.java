package com.kopylov.onlineshop.dao.jdbc.mapper;

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
        Timestamp creationDate = resultSet.getTimestamp("creation_date");
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .creationDate(creationDate.toLocalDateTime())
                .build();
    }
}
