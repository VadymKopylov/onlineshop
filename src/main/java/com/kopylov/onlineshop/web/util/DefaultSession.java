package com.kopylov.onlineshop.web.util;

import com.kopylov.onlineshop.back.entity.Product;
import com.kopylov.onlineshop.back.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DefaultSession {
    private String token;
    private LocalDateTime expireDate;
    private User user;
    private List<Product> cart;
    private String attribute;
}
