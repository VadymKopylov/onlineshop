package com.kopylov.onlineshop.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private final String email;
    private final String password;
    private String salt;
    private UserRole role;
}
