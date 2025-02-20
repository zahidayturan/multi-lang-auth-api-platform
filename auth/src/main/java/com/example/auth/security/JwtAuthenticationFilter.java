package com.example.auth.security;

import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = request.getHeader("Authorization");
        
        if (token != null && JwtTokenUtil.validateToken(token)) {
            String username = JwtTokenUtil.getUsernameFromToken(token);
            
            SecurityContextHolder.getContext().setAuthentication((Authentication) new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));

            System.out.println("Authenticated user: " + username);
        }

        try {
            filterChain.doFilter(request, response);
        } catch (java.io.IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}


