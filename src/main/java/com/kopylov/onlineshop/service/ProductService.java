package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.dao.jdbc.ProductsDao;
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
        return productsDao.findById(id);
    }

    public void addToDataBase(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productsDao.add(product);
    }

    public void deleteById(int id) {
            productsDao.deleteById(id);
    }

    public void update(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productsDao.update(product);
    }

    public List<Product> findAll(String value) {
        List<Product> allProducts = productsDao.findAll();
        if(allProducts == null){
            return null;
        }
        return sortByCriteria(setFormattedTime(allProducts), value);
    }

    public List<Product> findByName(String name, String value) {
        List<Product> productByName = productsDao.findByName(name);
        if(productByName == null){
            return null;
        }
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

    private List<Product> sortByCriteria(List<Product> products, String criteria) {
        if (criteria == null || criteria.equals("name")) {
            return sorted(products, Comparator.comparing(Product::getName));
        } else if (criteria.equals("price")) {
            return sorted(products, Comparator.comparing(Product::getPrice));
        } else if (criteria.equals("date")) {
            return sorted(products, Comparator.comparing(Product::getCreationDate));
        } else {
            throw new IllegalArgumentException("Invalid sort parameter: " + criteria);
        }
    }

    private static List<Product> sorted(List<Product> products, Comparator<Product> comparator) {
        return products.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
