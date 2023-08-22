package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.entity.User;
import com.kopylov.onlineshop.dao.jdbc.UserDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    private  UserDao userDao;

    public void save(User user) {
        userDao.add(user);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
