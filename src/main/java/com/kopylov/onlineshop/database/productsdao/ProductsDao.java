package com.kopylov.onlineshop.database.productsdao;

import com.kopylov.onlineshop.entity.Product;

import java.util.List;

public interface ProductsDao {

    void addToDataBase(Product product);

    void update(Product product);

    List<Product> findAll();

    void deleteById(int id);

    List<Product> findById(int id);

    void deleteAll();

    int count();
}
