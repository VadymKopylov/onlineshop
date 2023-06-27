package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.back.entity.Credentials;
import com.kopylov.onlineshop.back.entity.User;
import com.kopylov.onlineshop.back.entity.UserRole;
import com.kopylov.onlineshop.back.service.SecurityService;
import com.kopylov.onlineshop.back.service.UserService;
import com.kopylov.onlineshop.web.util.DefaultSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SecurityServiceTest {
    private final UserService userService = mock(UserService.class);
    private final SecurityService securityService = new SecurityService(userService, 180);
    private final Credentials credentials = new Credentials("test@gmail.com", "password");

    @Test
    void testLoginReturnSessionWhenUserExist() {
        DefaultSession session = securityService.login(credentials);

        assertNotNull(session);
    }

    @Test
    void testLogoutRemoveCorrectlySessionFromSessionMap() {
        DefaultSession session = securityService.login(credentials);
        String token = session.getToken();
        assertNotNull(session);

        securityService.logout(token);
        Map<String, DefaultSession> sessionsMap = securityService.getSessionsMap();

        assertEquals(sessionsMap.size(), 0);
    }

    @Test
    void testCreateSessionReturnCorrectSession() {
        DefaultSession session = securityService.createSession(mock(User.class));

        assertNotNull(session.getToken());
        assertNotNull(session.getExpireDate());
        assertNotNull(session.getUser());
        assertNotNull(session.getCart());
        assertNull(session.getAttribute());

    }

    @Test
    void testGetSessionReturnNotNullSession() {
        DefaultSession expectedSession = securityService.createSession(mock(User.class));

        String token = expectedSession.getToken();

        assertNotNull(expectedSession);
        DefaultSession actualSession = securityService.getSession(token);

        assertNotNull(actualSession);
        assertEquals(expectedSession, actualSession);
    }

    @Test
    void testGetGuestSessionReturnCorrectSession() {
        DefaultSession guestSession = securityService.createGuestSession();

        assertNotNull(guestSession);
        assertNull(guestSession.getToken());
        assertNull(guestSession.getExpireDate());
        assertNull(guestSession.getUser());
        assertNull(guestSession.getCart());
        assertNull(guestSession.getAttribute());
    }

    @Test
    void testFillUserReturnCorrectFieldsFromCredentials() {
        User expectedUser = new User()
                .setRole(UserRole.USER)
                .setCredentials(credentials);

        User actualUser = securityService.fillUser(credentials);

        assertEquals(expectedUser.getRole(), actualUser.getRole());
        assertEquals(expectedUser.getCredentials().getEmail(), actualUser.getCredentials().getEmail());
        assertNotNull(actualUser.getCredentials().getPassword());
        assertNotNull(actualUser.getSalt());
    }

    @Test
    void testIsPasswordMatchReturnTrueAfterComparePasswords() {
        String salt = "ABCDEFGHIJKLMNOP";
        String hashedPassword = DigestUtils.md5Hex("Password" + salt);
        Credentials hashedCredentials = new Credentials(credentials.getEmail(), hashedPassword);
        User user = new User()
                .setCredentials(hashedCredentials)
                .setSalt(salt);
        assertTrue(securityService.isPasswordMatch(user, "Password"));
    }

    @Test
    void testAssignTokenReturnNotNull() {
        String token = securityService.assignToken();

        assertNotNull(token);
    }

    @Test
    void testGenerateRandomSaltReturnNoyNullSaltAndCorrectSaltLength() {
        String salt = securityService.generateRandomSalt();

        assertNotNull(salt);
        assertEquals(16, salt.length());
    }
}
