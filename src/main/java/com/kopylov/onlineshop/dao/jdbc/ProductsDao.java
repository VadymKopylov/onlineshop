package com.kopylov.onlineshop.dao.jdbc;

import com.kopylov.onlineshop.back.entity.Product;

import java.util.List;

public interface ProductsDao {

    void add(Product product);

    List<Product> findAll();

    Product findById(int id);

    List<Product> findByName(String name);

    void update(Product product);

    void deleteById(int id);


}
