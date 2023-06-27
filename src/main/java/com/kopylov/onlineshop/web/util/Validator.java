package com.kopylov.onlineshop.web.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Validator {

    public static void validateProductDetails(String productName, String price) {
        if (StringUtils.isBlank(productName)) {
            throw new IllegalArgumentException("Invalid product details: productName");
        } else if (!NumberUtils.isParsable(price)) {
            throw new IllegalArgumentException("Invalid product details: price");
        } else if (Double.parseDouble(price) <= 0) {
            throw new IllegalArgumentException("Invalid product details: price cannot be less than or equal to 0");
        }
    }

    public static void validateProductUpdate(String id, String productName, String price) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Invalid product details: id doesn't exist");
        } else if (StringUtils.isBlank(productName)) {
            throw new IllegalArgumentException("Invalid product details: productName");
        } else if (!NumberUtils.isParsable(price)) {
            throw new IllegalArgumentException("Invalid product details: price");
        } else if (Double.parseDouble(price) <= 0) {
            throw new IllegalArgumentException("Invalid product details: price cannot be less than or equal to 0");
        }
    }

    public static void validateCredentials(String email, String password) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("Email are required");
        } else if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Password are required");
        }
    }
}
