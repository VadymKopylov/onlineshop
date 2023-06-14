package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.entity.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;

    public void addToCart(List<Product> cart, int id) {
        Product byId = productService.findById(id);
        byId.setId(cart.size());
        cart.add(byId);
    }

    public void deleteFromCart(List<Product> cart, int id) {
        cart.remove(id);
    }
}
