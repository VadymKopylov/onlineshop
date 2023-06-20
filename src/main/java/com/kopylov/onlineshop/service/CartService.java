package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;

    public void addToCart(List<Product> cart, int id) {
        Product product = productService.findById(id);
        product.setId(cart.size());
        cart.add(product);
    }

    public void deleteFromCart(List<Product> cart, int id) {
        cart.remove(id);
    }
}
