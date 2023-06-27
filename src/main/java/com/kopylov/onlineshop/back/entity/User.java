package com.kopylov.onlineshop.back.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class User {

    private Credentials credentials;
    private String salt;
    private UserRole role;
}
