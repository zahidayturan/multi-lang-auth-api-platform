package com.example.auth.controller;

import com.example.auth.model.dto.AuthResponseDTO;
import com.example.auth.model.dto.UserDTO;
import com.example.auth.model.User;
import com.example.auth.security.JwtTokenProvider;
import com.example.auth.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public AuthResponseDTO authenticateUser(@Validated @RequestBody UserDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String token = tokenProvider.generateToken(authentication);
        return new AuthResponseDTO(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody UserDTO signUpRequest) {
        try {
            User user = new User();
            user.setUsername(signUpRequest.getUsername());
            user.setPassword(signUpRequest.getPassword());
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}