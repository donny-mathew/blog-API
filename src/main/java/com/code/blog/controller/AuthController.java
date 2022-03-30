package com.code.blog.controller;

import com.code.blog.Security.CustomUserDetailsService;
import com.code.blog.Security.JwtTokenProvider;
import com.code.blog.entity.Role;
import com.code.blog.entity.User;
import com.code.blog.exception.InvalidRoleException;
import com.code.blog.payload.JwtAuthResponse;
import com.code.blog.payload.SignInDto;
import com.code.blog.payload.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        Role role = new Role();
        if(userDetailsService.existsByEmailOrUsername(signUpDto.getEmail(), signUpDto.getUsername()))
            return new ResponseEntity<>("Username or Email already exists!", HttpStatus.BAD_REQUEST);

        if(signUpDto.getRole().equalsIgnoreCase("admin"))
            role = userDetailsService.getRole("ROLE_ADMIN");
        else if (signUpDto.getRole().equalsIgnoreCase("user"))
            role = userDetailsService.getRole("ROLE_USER");
        else
            throw new InvalidRoleException("Invalid role type");

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setRoles(Collections.singleton(role));

        userDetailsService.saveUser(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInDto signInDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDto.getUsernameOrEmail(), signInDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }
}
