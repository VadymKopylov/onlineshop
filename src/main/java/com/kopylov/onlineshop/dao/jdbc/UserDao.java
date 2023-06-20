package com.kopylov.onlineshop.dao.jdbc;

import com.kopylov.onlineshop.entity.User;

public interface UserDao {

    void addToDataBase(User user);

    User findByEmail(String user);

    boolean isExist(String email);
}
