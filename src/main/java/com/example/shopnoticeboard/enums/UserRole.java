package com.example.shopnoticeboard.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    USER("ROLE_USER"),
    SHOPOWNER("ROLE_SHOPOWNER"),
    ADMIN("ROLE_ADMIN");

    private String value;
}
