package com.example.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenUtil {

    // Use a SecretKey for signing instead of a plain String
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("secret_key_example".getBytes());

    // Token oluşturma
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 saat geçerli
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Use SECRET_KEY with SignatureAlgorithm
                .compact();
    }

    // Token doğrulama
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Token'dan kullanıcı adı alma
    public static String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
