package com.authguard.authguard_user_service.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.authguard.authguard_user_service.model.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SecretJwtKey = "ddgdbydjsmsjjsmhdgdndjsksjbdddjdkddk";

    private SecretKey generateSecretKey() {
        return Keys.hmacShaKeyFor(SecretJwtKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(User client) {
        return Jwts.builder().subject(client.getUserId().toString()).claim("userType", "Client")
                .claim("email", client.getUsername()).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 3)).signWith(generateSecretKey())
                .compact();
    }

    public String createRefreshToken(User client) {
        return Jwts.builder().subject(client.getUserId().toString()).claim("email", client.getUsername())
                .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(generateSecretKey()).compact();
    }
}
