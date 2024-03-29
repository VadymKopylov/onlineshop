package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.entity.Credentials;
import com.kopylov.onlineshop.entity.ProductDto;
import com.kopylov.onlineshop.entity.User;
import com.kopylov.onlineshop.entity.UserRole;
import com.kopylov.onlineshop.web.util.DefaultSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor
@AllArgsConstructor
public class SecurityService {

    private final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int soleLength = 16;
    private UserService userService;
    private long sessionTimeToLive;
    private final Map<String, DefaultSession> sessionsMap = Collections.synchronizedMap(new HashMap<>());

    public DefaultSession login(Credentials credentials) {
        User user = userService.findByEmail(credentials.getEmail());
        if (user != null) {
            if (isPasswordMatch(user, credentials.getPassword())) {
                return createSession(user);
            }
        } else {
            user = fillUser(credentials);
            userService.save(user);
            return createSession(user);
        }
        return null;
    }

    public void logout(String token) {
        sessionsMap.remove(token);
    }

    public DefaultSession createSession(User user) {
        String token = assignToken();
        LocalDateTime expireDate = LocalDateTime.now().plusMinutes(sessionTimeToLive);
        List<ProductDto> cart = new ArrayList<>();
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

    public DefaultSession createGuestSession() {
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
        Credentials hashedCredentials = new Credentials(credentials.getEmail(), hashedPassword);
        return new User()
                .setRole(UserRole.USER)
                .setCredentials(hashedCredentials)
                .setSalt(salt);
    }

    public boolean isPasswordMatch(User user, String password) {
        String hashedPassword = DigestUtils.md5Hex(password + user.getSalt());
        return Objects.equals(hashedPassword, user.getCredentials().getPassword());
    }

    public String assignToken() {
        return UUID.randomUUID().toString();
    }

    public String generateRandomSalt() {
        SecureRandom random = new SecureRandom();
        return IntStream.range(0, soleLength)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }

    //Create for Test only
    public Map<String, DefaultSession> getSessionsMap() {
        return sessionsMap;
    }
}
