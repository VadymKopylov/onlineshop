package com.kopylov.onlineshop.back.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Product {
    private int id;
    private final String name;
    private final double price;
    private LocalDateTime creationDate;
}
