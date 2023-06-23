package com.kopylov.onlineshop.back.service;

import com.kopylov.onlineshop.back.entity.ProductDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;

    public void addToCart(List<ProductDto> cart, int id) {
        ProductDto productDto = productService.findById(id);
        cart.add(productDto);
    }

    public void deleteFromCart(List<ProductDto> cart, int id) {
        cart.removeIf(productDto -> productDto.getId() == id);
    }
}



