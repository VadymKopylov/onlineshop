package com.kopylov.onlineshop.entity;

public class User {
    private final String email;
    private final String password;
    private String salt;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String salt) {
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }
}
