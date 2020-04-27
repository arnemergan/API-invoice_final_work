package com.api.invoice.models;

public class Jwt {
    private final String jwttoken;

    public Jwt(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
