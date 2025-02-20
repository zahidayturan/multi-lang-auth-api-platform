package com.example.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF protection using Lambda DSL
            .headers(headers -> headers.frameOptions().sameOrigin())  // Set frame options to sameOrigin
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/**").permitAll()  // Allow all requests to all paths
                .anyRequest().permitAll()  // Allow other requests
            );
        return http.build();
    }
}
