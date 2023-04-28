package com.kopylov.onlineshop.database.productsdao;

import com.kopylov.onlineshop.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcProductsDaoITest {
    @Test
    void testFindAllReturnCorrectData() {
        JdbcProductsDao jdbcProductsDao = new JdbcProductsDao();
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