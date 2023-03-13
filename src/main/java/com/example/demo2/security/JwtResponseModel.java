package com.example.demo2.security;


public class JwtResponseModel {

    private final String token;
    public JwtResponseModel(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}