package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.dao.UserDao;
import com.kopylov.onlineshop.entity.User;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean isExist(User user) {
        if (!user.getEmail().isEmpty() || user.getEmail() != null) {
            if (!user.getPassword().isEmpty() || user.getPassword() != null) {
                return userDao.isExist(user.getEmail());
            }
        }
        return false;
    }

    public void addToDataBase(User user){
        userDao.addToDataBase(user);
    }
    public User findByEmail(String email){
        return userDao.findByEmail(email);
    }
}
