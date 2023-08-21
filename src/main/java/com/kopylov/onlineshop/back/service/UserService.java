package com.kopylov.onlineshop.back.service;

import com.kopylov.onlineshop.back.entity.User;
import com.kopylov.onlineshop.dao.jdbc.UserDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    private  UserDao userDao;

    public boolean isExist(String email) {
        return userDao.isExist(email);
    }

    public void save(User user) {
        userDao.add(user);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
