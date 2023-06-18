package com.kopylov.onlineshop.web.security;

import com.kopylov.onlineshop.entity.UserRole;
import com.kopylov.onlineshop.service.SecurityService;

public class UserSecurityFilter extends SecurityFilter {

    public UserSecurityFilter(SecurityService securityService) {
        super(securityService);
    }

    @Override
    UserRole requiredRole() {
        return UserRole.USER;
    }
}

