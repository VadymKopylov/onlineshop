package com.kopylov.onlineshop.dao;

import com.kopylov.onlineshop.entity.Product;

import java.util.List;

public interface ProductsDao {

    void addToDataBase(Product product);

    void update(Product product);

    List<Product> findAll();

    void deleteById(int id);

    List<Product> findById(int id);

    List<Product> findByName(String name);
}
