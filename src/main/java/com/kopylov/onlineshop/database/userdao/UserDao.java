package com.kopylov.onlineshop.database.userdao;

import com.kopylov.onlineshop.entity.User;

public interface UserDao {

    void addToDataBase(User user);

    User findUserByEmail(String user);

    boolean isExist(String email);

}
