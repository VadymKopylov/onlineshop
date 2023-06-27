package com.kopylov.onlineshop.back.service;

import com.kopylov.onlineshop.back.entity.Product;
import com.kopylov.onlineshop.back.entity.ProductDto;
import com.kopylov.onlineshop.dao.jdbc.ProductsDao;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductService {

    private final ProductsDao productsDao;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private Map<String, ProductDto> productsCache;

    public void initializeCache() {
        productsCache = productsDao.findAll().stream()
                .map(this::toProductDto)
                .collect(Collectors.toConcurrentMap(ProductDto::getId, Function.identity()));
    }

    public void addToDataBase(Product product) {
        product.setId(generateId());
        product.setCreationDate(LocalDateTime.now());
        productsDao.add(product);
        productsCache.put(product.getId(), toProductDto(product));
    }

    public List<ProductDto> findAll(String sortCriteria) {
        return productsCache.values()
                .stream()
                .sorted(createComparatorByCriteria(sortCriteria))
                .collect(Collectors.toList());
    }

    public ProductDto findById(String id) {
        return productsCache.values().stream()
                .filter(productDto -> productDto.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<ProductDto> findByName(String name, String sortCriteria) {
        return productsCache.values().stream()
                .filter(productDto -> productDto.getName().equalsIgnoreCase(name))
                .sorted(createComparatorByCriteria(sortCriteria))
                .collect(Collectors.toList());
    }

    public void update(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productsDao.update(product);
        productsCache.put(product.getId(), toProductDto(product));
    }

    public void deleteById(String id) {
        productsDao.deleteById(id);
        productsCache.remove(id);
    }

    private LocalDateTime setFormattedTime(LocalDateTime creationDate) {
        String formattedDateTime = creationDate.format(DATE_TIME_FORMATTER);
        return LocalDateTime.parse(formattedDateTime, DATE_TIME_FORMATTER);
    }

    private Comparator<ProductDto> createComparatorByCriteria(String sortCriteria) {
        if (Objects.equals("name", sortCriteria)) {
            return Comparator.comparing(ProductDto::getName);
        } else if (Objects.equals("price", sortCriteria)) {
            return Comparator.comparing(ProductDto::getPrice);
        } else if (Objects.equals("date", sortCriteria)) {
            return Comparator.comparing(ProductDto::getCreationDate);
        } else {
            return Comparator.comparing(ProductDto::getName);
        }
    }

    private ProductDto toProductDto(Product product) {
        return new ProductDto()
                .setId(product.getId())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setCreationDate(setFormattedTime(product.getCreationDate()));
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
