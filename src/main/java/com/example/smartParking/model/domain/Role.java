package com.example.smartParking.model.domain;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    USER, MANAGER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
