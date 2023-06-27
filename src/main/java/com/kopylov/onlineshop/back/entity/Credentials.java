package com.kopylov.onlineshop.back.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Credentials {

    private final String email;
    private final String password;
}
