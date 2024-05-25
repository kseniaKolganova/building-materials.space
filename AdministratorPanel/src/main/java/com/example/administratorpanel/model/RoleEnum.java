package com.example.administratorpanel.model;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ADMIN,
    MANAGER;
    @Override
    public String getAuthority()
    {
        return name();
    }
}
