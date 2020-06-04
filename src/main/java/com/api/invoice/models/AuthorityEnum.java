package com.api.invoice.models;

public enum AuthorityEnum {
    ADMIN("ROLE_ADMIN"),
    VIEW("ROLE_VIEW"),
    EDIT("ROLE_EDIT");

    private String auth;

    AuthorityEnum(String auth) {
        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }
}
