package com.kopylov.onlineshop.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Credentials credentials;
    private String salt;
    private UserRole role;
}
