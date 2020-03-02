package com.loloara.genreisromance.security.jwt;

import com.loloara.genreisromance.model.BaseModel;

public class JwtAuthResponse extends BaseModel {

    private final String token;

    public JwtAuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
