package com.kopylov.onlineshop.entity;

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
