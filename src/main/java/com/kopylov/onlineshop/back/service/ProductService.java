package com.kopylov.onlineshop.back.service;

import com.kopylov.onlineshop.back.entity.ProductDto;
import com.kopylov.onlineshop.dao.jdbc.ProductsDao;
import com.kopylov.onlineshop.back.entity.Product;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductService {
    private final ProductsDao productsDao;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private List<ProductDto> dtoProducts = Collections.synchronizedList(new ArrayList<>());

    public void addToDataBase(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productsDao.add(product);
    }

    public ProductDto findById(int id) {
        return dtoProducts.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<ProductDto> findAll(String value) {
        dtoProducts = toDtoList(productsDao.findAll());
        if (dtoProducts.isEmpty()) {
            return null;
        }
        return sortByCriteria(dtoProducts, value);
    }

    public List<ProductDto> findByName(String name, String value) {
        List<ProductDto> filteredProducts = dtoProducts.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        return sortByCriteria(filteredProducts, value);
    }

    public void update(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productsDao.update(product);
    }

    public void deleteById(int id) {
        productsDao.deleteById(id);
    }

    private LocalDateTime setFormattedTime(LocalDateTime creationDate) {
        String formattedDateTime = creationDate.format(DATE_TIME_FORMATTER);
        return LocalDateTime.parse(formattedDateTime, DATE_TIME_FORMATTER);
    }

    private List<ProductDto> sortByCriteria(List<ProductDto> products, String criteria) {
        if (criteria == null || criteria.equals("name")) {
            return sorted(products, Comparator.comparing(ProductDto::getName));
        } else if (criteria.equals("price")) {
            return sorted(products, Comparator.comparing(ProductDto::getPrice));
        } else if (criteria.equals("date")) {
            return sorted(products, Comparator.comparing(ProductDto::getCreationDate));
        } else {
            throw new IllegalArgumentException("Invalid sort parameter: " + criteria);
        }
    }

    private static List<ProductDto> sorted(List<ProductDto> products, Comparator<ProductDto> comparator) {
        return products.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private List<ProductDto> toDtoList(List<Product> productList) {
        return productList.stream()
                .map(product -> new ProductDto()
                        .setId(product.getId())
                        .setName(product.getName())
                        .setPrice(product.getPrice())
                        .setCreationDate(setFormattedTime(product.getCreationDate())))
                .collect(Collectors.toList());
    }
}
