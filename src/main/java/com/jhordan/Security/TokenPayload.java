package com.jhordan.Security;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jhordan.Entity.User;

public class TokenPayload {
    private final String username;
    private final User.Role role;

    public TokenPayload(String username, User.Role role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public User.Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", username)
                .append("role", role)
                .toString();
    }
}