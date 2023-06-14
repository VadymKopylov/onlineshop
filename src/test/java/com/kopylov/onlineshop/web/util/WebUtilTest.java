/*
package com.kopylov.onlineshop.web.util;

import com.kopylov.onlineshop.entity.Product;
import com.kopylov.onlineshop.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebUtilTest {
    private final HttpServletRequest mockRequest = mock(HttpServletRequest.class);

    @Test
    void testGetProductReturnCorrectParametersFromRequest() {
        when(mockRequest.getParameter("productName")).thenReturn("Car");
        when(mockRequest.getParameter("price")).thenReturn(String.valueOf(1500.00));

        Product product = WebUtil.getProduct(mockRequest);

        assertEquals("Car", product.getName());
        assertEquals(1500.00, product.getPrice());
    }

    @Test
    void testGetProductByIdReturnCorrectParametersFromRequest() {
        when(mockRequest.getParameter("productId")).thenReturn(String.valueOf(1));
        when(mockRequest.getParameter("productName")).thenReturn("Car");
        when(mockRequest.getParameter("price")).thenReturn(String.valueOf(3500.00));

        Product product = WebUtil.updateProduct(mockRequest);

        assertEquals(1, product.getId());
        assertEquals("Car", product.getName());
        assertEquals(3500.00, product.getPrice());
    }

    @Test
    void testGetUserReturnCorrectParametersFromRequest() {
        when(mockRequest.getParameter("email")).thenReturn("test@google.com");
        when(mockRequest.getParameter("password")).thenReturn("testpassword");

        User user = WebUtil.getCredentials(mockRequest);

        assertEquals("test@google.com", user.getEmail());
        assertEquals("testpassword", user.getPassword());
    }

    @Test
    void testGetTokenReturnCorrectCookieFromRequest() {
        Cookie mockCookie = mock(Cookie.class);
        Cookie[] cookies = new Cookie[]{mockCookie};

        when(mockCookie.getName()).thenReturn("user-token");
        when(mockCookie.getValue()).thenReturn("randomToken");
        when(mockRequest.getCookies()).thenReturn(cookies);

        String token = WebUtil.getToken(mockRequest);

        assertEquals("randomToken", token);
    }
}*/
