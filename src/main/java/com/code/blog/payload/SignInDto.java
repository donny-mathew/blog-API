package com.code.blog.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SignInDto {
    private String usernameOrEmail;
    private String password;
}
