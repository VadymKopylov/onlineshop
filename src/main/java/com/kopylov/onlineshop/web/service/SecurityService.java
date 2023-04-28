package com.kopylov.onlineshop.web.service;

import com.kopylov.onlineshop.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SecurityService {
    private List<String> userTokens = new ArrayList<>();


    public User fillUser(User user) {
        String salt = generateRandomSalt();
        String hashedPassword = DigestUtils.md5Hex(user.getPassword() + salt);
        return new User(user.getEmail(), hashedPassword, salt);
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
        if (Objects.equals(hashedPassword, user.getPassword())) {
            return true;
        }
        return false;
    }

    public String assignToken() {
        String userToken = UUID.randomUUID().toString();
        userTokens.add(userToken);
        return userToken;
    }

    private static String generateRandomSalt() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int length = 16;
        SecureRandom random = new SecureRandom();
        return IntStream.range(0, length)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }
}
