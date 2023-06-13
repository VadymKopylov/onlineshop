package com.kopylov.onlineshop.service;

import com.kopylov.onlineshop.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityServiceTest {
    private final UserService userService = mock(UserService.class);
    private final SecurityService securityService = new SecurityService(userService);

    @Test
    void testLoginReturnTrueIfUserExist() {
        User user = new User("test@example.com", "password");
        User existingUser = securityService.fillUser(user);

        when(userService.isExist(user)).thenReturn(true);
        when(userService.findByEmail(user.getEmail())).thenReturn(existingUser);

        String token = securityService.login(user);

        assertNotNull(token);
        assertTrue(securityService.isAuth(token));
    }

    @Test
    void testFillUserReturnNotNullUserFields() {
        User user = new User("test@example.com", "password");

        User filledUser = securityService.fillUser(user);

        assertNotNull(filledUser.getEmail());
        assertNotNull(filledUser.getPassword());
        assertNotNull(filledUser.getSalt());
        assertNotEquals(user.getPassword(), filledUser.getPassword());
    }

    @Test
    void testIsAuthReturnTrueWhenTokenIsExist() {
        String existingToken = securityService.assignToken();
        String nonExistingToken = "ABCDEFGHIJKLMNOP";

        assertTrue(securityService.isAuth(existingToken));
        assertFalse(securityService.isAuth(nonExistingToken));
    }

    @Test
    void testIsPasswordMatchReturnTrueAfterComparePasswords() {
        String hashedPassword = DigestUtils.md5Hex("Password" + "ABCDEFGHIJKLMNOP");
        User user = new User("test@email.com", hashedPassword, "ABCDEFGHIJKLMNOP");

        assertTrue(securityService.isPasswordMatch(user, "Password"));
    }

    @Test
    void testAssignTokenReturnTrueWhenTokenExist() {
        String token = securityService.assignToken();

        assertNotNull(token);
        assertTrue(securityService.isAuth(token));
    }

    @Test
    void testAssignTokenReturnFalseWhenTokenNotExist() {
        String wrongToken = "NonExistentToken";

        assertFalse(securityService.isAuth(wrongToken));
    }

    @Test
    void testGenerateRandomSaltReturnNoyNullSaltAndCorrectSaltLength() {
        String salt = securityService.generateRandomSalt();

        assertNotNull(salt);
        assertEquals(16, salt.length());
    }
}