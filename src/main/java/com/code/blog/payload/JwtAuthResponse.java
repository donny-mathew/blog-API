package com.code.blog.payload;

import lombok.*;

@Getter
@Setter
public class JwtAuthResponse {
    private String token;
    private String tokenType = "Bearer";

    public JwtAuthResponse(String token) {
        this.token = token;
    }
}
