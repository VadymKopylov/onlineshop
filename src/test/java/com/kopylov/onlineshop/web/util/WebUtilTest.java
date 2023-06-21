package com.kopylov.onlineshop.web.util;

import com.kopylov.onlineshop.back.entity.Credentials;
import com.kopylov.onlineshop.back.entity.Product;
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
        when(mockRequest.getParameter("price")).thenReturn("1500");

        Product product = WebUtil.getProduct(mockRequest);

        assertEquals("Car", product.getName());
        assertEquals(1500.00, product.getPrice());
    }

    @Test
    void testGetProductThrowIllegalArgumentExceptionWhenProductNameIsNull() {
        when(mockRequest.getParameter("productName")).thenReturn(null);
        when(mockRequest.getParameter("price")).thenReturn(String.valueOf(1500.00));

        assertThrows(IllegalArgumentException.class, () -> WebUtil.getProduct(mockRequest));
    }

    @Test
    void testGetProductThrowIllegalArgumentExceptionWhenProductNameIsEmpty() {
        when(mockRequest.getParameter("productName")).thenReturn("");
        when(mockRequest.getParameter("price")).thenReturn(String.valueOf(1500.00));

        assertThrows(IllegalArgumentException.class, () -> WebUtil.getProduct(mockRequest));
    }

    @Test
    void testGetProductThrowIllegalArgumentExceptionWhenProductPriceIsEmpty() {
        when(mockRequest.getParameter("productName")).thenReturn("Car");
        when(mockRequest.getParameter("price")).thenReturn("");

        assertThrows(IllegalArgumentException.class, () -> WebUtil.getProduct(mockRequest));
    }

    @Test
    void testGetProductThrowIllegalArgumentExceptionWhenProductPriceEqualsZero() {
        when(mockRequest.getParameter("productName")).thenReturn("Car");
        when(mockRequest.getParameter("price")).thenReturn("0");

        assertThrows(IllegalArgumentException.class, () -> WebUtil.getProduct(mockRequest));
    }

    @Test
    void testUpdateProductByIdReturnCorrectParametersFromRequest() {
        when(mockRequest.getParameter("id")).thenReturn(String.valueOf(1));
        when(mockRequest.getParameter("productName")).thenReturn("Car");
        when(mockRequest.getParameter("price")).thenReturn(String.valueOf(3500.00));

        Product product = WebUtil.updateProduct(mockRequest);

        assertEquals(1, product.getId());
        assertEquals("Car", product.getName());
        assertEquals(3500.00, product.getPrice());
    }

    @Test
    void testUpdateProductByIdThrowIllegalArgumentExceptionWhenIdIsZero() {
        when(mockRequest.getParameter("id")).thenReturn(String.valueOf(0));
        when(mockRequest.getParameter("productName")).thenReturn("Car");
        when(mockRequest.getParameter("price")).thenReturn(String.valueOf(3500.00));

        assertThrows(IllegalArgumentException.class, () -> WebUtil.updateProduct(mockRequest));
    }

    @Test
    void testUpdateProductByIdThrowIllegalArgumentExceptionWhenProductNameIsEmpty() {
        when(mockRequest.getParameter("id")).thenReturn(String.valueOf(1));
        when(mockRequest.getParameter("productName")).thenReturn("");
        when(mockRequest.getParameter("price")).thenReturn(String.valueOf(3500.00));

        assertThrows(IllegalArgumentException.class, () -> WebUtil.updateProduct(mockRequest));
    }

    @Test
    void testUpdateProductByIdThrowIllegalArgumentExceptionWhenProductNameIsNull() {
        when(mockRequest.getParameter("id")).thenReturn(String.valueOf(1));
        when(mockRequest.getParameter("productName")).thenReturn(null);
        when(mockRequest.getParameter("price")).thenReturn(String.valueOf(3500.00));

        assertThrows(IllegalArgumentException.class, () -> WebUtil.updateProduct(mockRequest));
    }

    @Test
    void testUpdateProductByIdThrowIllegalArgumentExceptionWhenPriceIsZero() {
        when(mockRequest.getParameter("id")).thenReturn(String.valueOf(1));
        when(mockRequest.getParameter("productName")).thenReturn("Car");
        when(mockRequest.getParameter("price")).thenReturn("0");

        assertThrows(IllegalArgumentException.class, () -> WebUtil.updateProduct(mockRequest));
    }

    @Test
    void testGetCredentialsReturnCorrectParametersFromRequest() {
        when(mockRequest.getParameter("email")).thenReturn("test@google.com");
        when(mockRequest.getParameter("password")).thenReturn("testpassword");

        Credentials credentials = WebUtil.getCredentials(mockRequest);

        assertEquals("test@google.com", credentials.getEmail());
        assertEquals("testpassword", credentials.getPassword());
    }

    @Test
    void testGetCredentialsThrowIllegalArgumentExceptionWhenEmptyEmailIsEntered() {
        when(mockRequest.getParameter("email")).thenReturn("");
        when(mockRequest.getParameter("password")).thenReturn("testpassword");

        assertThrows(IllegalArgumentException.class, () -> WebUtil.getCredentials(mockRequest));
    }

    @Test
    void testGetCredentialsThrowIllegalArgumentExceptionWhenEmptyPasswordIsEntered() {
        when(mockRequest.getParameter("email")).thenReturn("Test@gmail.com");
        when(mockRequest.getParameter("password")).thenReturn("");

        assertThrows(IllegalArgumentException.class, () -> WebUtil.getCredentials(mockRequest));
    }

    @Test
    void testGetCredentialsThrowIllegalArgumentExceptionWhenEmailIsNull() {
        when(mockRequest.getParameter("email")).thenReturn(null);
        when(mockRequest.getParameter("password")).thenReturn("testpassword");

        assertThrows(IllegalArgumentException.class, () -> WebUtil.getCredentials(mockRequest));
    }

    @Test
    void testGetCredentialsThrowIllegalArgumentExceptionWhenPasswordIsNull() {
        when(mockRequest.getParameter("email")).thenReturn("Test@gmail.com");
        when(mockRequest.getParameter("password")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> WebUtil.getCredentials(mockRequest));
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

    @Test
    void testGetTokenReturnNullIfThereIsNoCookieInTheRequest() {
        Cookie[] cookies = new Cookie[0];
        when(mockRequest.getCookies()).thenReturn(cookies);

        String token = WebUtil.getToken(mockRequest);

        assertNull(token);
    }
}
