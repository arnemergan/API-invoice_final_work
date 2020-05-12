package com.api.invoice.dto.request;

import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public class AuthoritiesChangerDTO {
    @NotNull(message = "authorities is required")
    private String username;
    @NotNull(message = "authorities is required")
    private List<String> authorities;

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
