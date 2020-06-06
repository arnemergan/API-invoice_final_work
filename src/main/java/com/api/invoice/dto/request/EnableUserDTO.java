package com.api.invoice.dto.request;

import javax.validation.constraints.NotNull;

public class EnableUserDTO {
    @NotNull(message = "username is required")
    private String username;
    @NotNull(message = "enable is required")
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
