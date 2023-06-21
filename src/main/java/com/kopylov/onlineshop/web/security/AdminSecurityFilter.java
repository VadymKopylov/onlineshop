package com.kopylov.onlineshop.web.security;

import com.kopylov.onlineshop.back.entity.UserRole;
import com.kopylov.onlineshop.back.service.SecurityService;

public class AdminSecurityFilter extends SecurityFilter {

    public AdminSecurityFilter(SecurityService securityService) {
        super(securityService);
    }

    @Override
    UserRole requiredRole() {
        return UserRole.ADMIN;
    }
}
