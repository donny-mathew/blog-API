package com.code.blog.payload;

import lombok.Data;

@Data
public class SignInDto {
    private String usernameOrEmail;
    private String password;
}
