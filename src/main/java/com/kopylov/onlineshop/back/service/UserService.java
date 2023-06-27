package com.kopylov.onlineshop.back.service;

import com.kopylov.onlineshop.back.entity.User;
import com.kopylov.onlineshop.dao.jdbc.UserDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public boolean isExist(String email) {
        return userDao.isExist(email);
    }

    public void addToDataBase(User user) {
        userDao.addToDataBase(user);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
