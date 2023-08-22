package com.kopylov.onlineshop.dao.jdbc;

import com.kopylov.onlineshop.entity.User;

public interface UserDao {

    void add(User user);

    User findByEmail(String user);
}
