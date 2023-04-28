package com.kopylov.onlineshop.entity;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime creationDate;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    //for Tests
    public Product(String name, double price,LocalDateTime creationDate) {
        this.name = name;
        this.price = price;
        this.creationDate = creationDate;
    }

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, double price, LocalDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}