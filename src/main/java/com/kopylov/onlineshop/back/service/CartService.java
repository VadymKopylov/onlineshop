package com.kopylov.onlineshop.back.service;

import com.kopylov.onlineshop.back.entity.ProductDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class CartService {

    private ProductService productService;

    public void addToCart(List<ProductDto> cart, String id) {
        ProductDto productDto = productService.findById(id);
        cart.add(productDto);
    }

    public void deleteFromCart(List<ProductDto> cart, String id) {
        cart.removeIf(productDto -> productDto.getId().equals(id));
    }
}



