package com.kopylov.onlineshop.entity.service;

import com.kopylov.onlineshop.database.userdao.UserDao;
import com.kopylov.onlineshop.entity.User;
import com.kopylov.onlineshop.web.service.SecurityService;

public class UserService {
    private final UserDao userDao;
    private final SecurityService securityService;

    public UserService(UserDao userDao, SecurityService securityService) {
        this.userDao = userDao;
        this.securityService = securityService;
    }

    public boolean isExist(User user) {
        if (!user.getEmail().isEmpty() || user.getEmail() != null) {
            if (!user.getPassword().isEmpty() || user.getPassword() != null) {
                return userDao.isExist(user.getEmail());
            }
        }
        return false;
    }

    public String login(User user) {
        if (!isExist(user)) {
            userDao.addToDataBase(securityService.fillUser(user));
            return securityService.assignToken();
        } else {
            if (securityService.isPasswordMatch(userDao.findUserByEmail(user.getEmail()), user.getPassword())) {
                return securityService.assignToken();
            }
        }
        return null;
    }
}
