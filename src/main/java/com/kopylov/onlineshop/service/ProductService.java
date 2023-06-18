package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.dao.ProductsDao;
import com.kopylov.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductService {
    private final ProductsDao productsDao;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Product findById(int id) {
        Product byId = productsDao.findById(id);
        if (byId != null) {
            return byId;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addToDataBase(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productsDao.addToDataBase(product);
    }

    public void deleteById(int id) {
        if (productsDao.findById(id) != null) {
            productsDao.deleteById(id);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void update(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productsDao.update(product);
    }

    public List<Product> findAll(String value) {
        List<Product> allProducts = productsDao.findAll();
        return sortByCriteria(setFormattedTime(allProducts), value);
    }

    public List<Product> findByName(String name, String value) {
        List<Product> productByName = productsDao.findByName(name);
        return sortByCriteria(setFormattedTime(productByName), value);
    }

    private List<Product> setFormattedTime(List<Product> products) {
        for (Product allProduct : products) {
            LocalDateTime creationDate = allProduct.getCreationDate();
            String formattedDateTime = creationDate.format(DATE_TIME_FORMATTER);
            allProduct.setCreationDate(LocalDateTime.parse(formattedDateTime, DATE_TIME_FORMATTER));
        }
        return products;
    }

    private List<Product> sortByCriteria(List<Product> products, String value) {
        if (value == null || value.equals("name")) {
            return products.stream()
                    .sorted(Comparator.comparing(Product::getName))
                    .collect(Collectors.toList());
        } else if (value.equals("price")) {
            return products.stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .collect(Collectors.toList());
        } else if (value.equals("date")) {
            return products.stream()
                    .sorted(Comparator.comparing(Product::getCreationDate))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Invalid sort parameter: " + value);
        }
    }
}
