package com.kopylov.onlineshop.web.util;

import com.kopylov.onlineshop.entity.ProductDto;
import com.kopylov.onlineshop.entity.User;
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
    private List<ProductDto> cart;
    private String attribute;
}
