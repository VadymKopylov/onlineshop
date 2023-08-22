package com.kopylov.onlineshop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Setter
@Getter
@Accessors(chain = true)
public class ProductDto {

    private String id;
    private String name;
    private double price;
    private LocalDateTime creationDate;
}
