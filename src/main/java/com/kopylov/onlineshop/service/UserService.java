package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.dao.UserDao;
import com.kopylov.onlineshop.entity.User;
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
