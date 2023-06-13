package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SecurityService {
    private final UserService userService;
    private final List<String> userTokens = Collections.synchronizedList(new ArrayList<>());

    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public String login(User user) {
        if (!userService.isExist(user)) {
            userService.addToDataBase(fillUser(user));
            return assignToken();
        } else {
            if (isPasswordMatch(userService.findByEmail(user.getEmail()), user.getPassword())) {
                return assignToken();
            }
        }
        return null;
    }

    public User fillUser(User user) {
        String salt = generateRandomSalt();
        String hashedPassword = DigestUtils.md5Hex(user.getPassword() + salt);
        return User.builder()
                .email(user.getEmail())
                .password(hashedPassword)
                .salt(salt)
                .build();
    }

    public boolean isAuth(String token) {
        for (String userToken : userTokens) {
            if (userToken.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPasswordMatch(User user, String password) {
        String hashedPassword = DigestUtils.md5Hex(password + user.getSalt());
        return Objects.equals(hashedPassword, user.getPassword());
    }

    public String assignToken() {
        String userToken = UUID.randomUUID().toString();
        userTokens.add(userToken);
        return userToken;
    }

    String generateRandomSalt() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int length = 16;
        SecureRandom random = new SecureRandom();
        return IntStream.range(0, length)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }
}
