package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.entity.ProductDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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



