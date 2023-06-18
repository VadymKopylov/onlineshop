package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.entity.Credentials;
import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.entity.User;
import com.kopylov.onlineshop.entity.UserRole;
import com.kopylov.onlineshop.web.util.DefaultSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;
    private final long sessionTimeToLive;
    private final Map<String, DefaultSession> sessionsMap = Collections.synchronizedMap(new HashMap<>());

    //We don't use registration, so the user is filled in from the credentials
    public DefaultSession login(Credentials credentials) {
        if (!userService.isExist(credentials.getEmail())) {
            User user = fillUser(credentials);
            userService.addToDataBase(user);
            return createSession(user);
        } else {
            User user = userService.findByEmail(credentials.getEmail());
            if (isPasswordMatch(user, credentials.getPassword())) {
                return createSession(user);
            }
        }
        return null;
    }

    public void logout(String token) {
        sessionsMap.remove(token);
    }

    public DefaultSession createSession(User user) {
        String token = assignToken();
        LocalDateTime expireDate = LocalDateTime.now().plusMinutes(sessionTimeToLive);
        List<Product> cart = new ArrayList<>();
        DefaultSession session = DefaultSession.builder()
                .token(token)
                .expireDate(expireDate)
                .user(user)
                .cart(cart)
                .attribute(null)
                .build();
        sessionsMap.put(token, session);
        return session;
    }

    public DefaultSession getSession(String token) {
        DefaultSession session = sessionsMap.get(token);
        if (session == null) {
            return null;
        }
        if (session.getExpireDate().isBefore(LocalDateTime.now())) {
            sessionsMap.remove(token);
            return null;
        }
        return session;
    }

    public DefaultSession getGuestSession() {
        return DefaultSession.builder()
                .token(null)
                .expireDate(null)
                .user(null)
                .cart(null)
                .attribute(null)
                .build();
    }

    public User fillUser(Credentials credentials) {
        String salt = generateRandomSalt();
        String hashedPassword = DigestUtils.md5Hex(credentials.getPassword() + salt);
        return User.builder()
                .role(UserRole.USER)
                .email(credentials.getEmail())
                .password(hashedPassword)
                .salt(salt)
                .build();
    }

    public boolean isPasswordMatch(User user, String password) {
        String hashedPassword = DigestUtils.md5Hex(password + user.getSalt());
        return Objects.equals(hashedPassword, user.getPassword());
    }

    public String assignToken() {
        return UUID.randomUUID().toString();
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
