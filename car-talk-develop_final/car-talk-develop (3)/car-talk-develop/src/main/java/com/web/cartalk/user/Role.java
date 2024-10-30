package com.web.cartalk.user;

public enum Role {
    ROLE_PENDING,
    ROLE_USER,
    ;

    public boolean canBeLoggedIn() {
        return this == ROLE_USER;
    }
}
