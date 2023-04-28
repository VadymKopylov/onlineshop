package com.kopylov.onlineshop.entity.service;

import com.kopylov.onlineshop.database.productsdao.ProductsDao;
import com.kopylov.onlineshop.entity.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductsDao productsDao;

    public ProductService(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    public List<Product> findAll() {
        return productsDao.findAll();
    }

    public List<Product> findProductById(int id) {
        List<Product> productById = productsDao.findById(id);
        if (productById != null) {
            return productById;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addToDataBase(Product product) {
        product.setCreationDate(getFormattedTime());
        productsDao.addToDataBase(product);
    }

    public void deleteProductById(int id) {
        if (productsDao.findById(id) != null) {
            productsDao.deleteById(id);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void deleteAllProducts() {
        productsDao.deleteAll();
    }

    public int countProduct() {
        return productsDao.count();
    }

    public void updateProduct(Product product) {
        product.setCreationDate(getFormattedTime());
        productsDao.update(product);
    }

    public List<Product> filter(String value) {
        if (value.equals("name")) {
            return productsDao.findAll().stream()
                    .sorted(Comparator.comparing(Product::getName))
                    .collect(Collectors.toList());
        } else if (value.equals("price")) {
            return productsDao.findAll().stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .collect(Collectors.toList());
        } else {
            return productsDao.findAll().stream()
                    .sorted(Comparator.comparing(Product::getCreationDate))
                    .collect(Collectors.toList());
        }
    }

    private static LocalDateTime getFormattedTime() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = time.format(formatter);
        return LocalDateTime.parse(formattedTime, formatter);
    }
}
