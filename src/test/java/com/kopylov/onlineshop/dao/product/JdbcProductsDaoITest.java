package com.kopylov.onlineshop.dao.product;

import com.kopylov.onlineshop.dao.jdbc.ConnectionFactory;
import com.kopylov.onlineshop.dao.jdbc.JdbcProductsDao;
import com.kopylov.onlineshop.back.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;


class JdbcProductsDaoITest {
    @Test
    void testFindAllReturnCorrectData() {
        Properties properties = new Properties();
        properties.setProperty("url","jdbc:postgresql://localhost:5432/shopdatabase");
        properties.setProperty("login","postgres");
        properties.setProperty("password","mysecretpassword");
        ConnectionFactory connectionFactory = new ConnectionFactory(properties);
        JdbcProductsDao jdbcProductsDao = new JdbcProductsDao(connectionFactory);
        List<Product> products = jdbcProductsDao.findAll();
        assertFalse(products.isEmpty());
        for (Product product : products) {
            assertNotEquals(0, product.getId());
            assertNotEquals(0, product.getPrice());
            assertNotNull(product.getName());
            assertNotNull(product.getCreationDate());
        }
    }
}