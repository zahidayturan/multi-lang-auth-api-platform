package com.example.auth.controller;

import com.example.auth.model.dto.AuthResponseDTO;
import com.example.auth.model.dto.UserDTO;
import com.example.auth.model.User;
import com.example.auth.security.JwtTokenProvider;
import com.example.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User registerUser(@Validated @RequestBody UserDTO signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        return userService.saveUser(user);
    }
}
