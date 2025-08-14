package com.authguard.authguard_user_service.service;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.authguard.authguard_user_service.model.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${jwt.privateKey}")
    private String privateKey;
    @Value("${jwt.publicKey}")
    private String publicKey;

    private static final String SecretJwtKey = "ddgdbydjsmsjjsmhdgdndjsksjbdddjdkddk";

    private RSAPublicKey getPublicKey() throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(decoded));
    }

    private RSAPrivateKey getPrivateKey() throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        return (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    private SecretKey generateSecretKey() {
        return Keys.hmacShaKeyFor(SecretJwtKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(User user) {
        return Jwts.builder().subject(user.getUserId().toString()).claim("userType", "Client")
                .claim("email", user.getUsername()).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 3)).signWith(generateSecretKey())
                .compact();
    }

    /*
     * Create refresh token
     * 
     * @param User client
     * 
     * @return refresh token of type String
     */
    public String createRefreshToken(User user) {
        return Jwts.builder().subject(user.getUserId()).claim("email", user.getUsername())
                .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(generateSecretKey()).compact();
    }

    /*
     * Verify service access token inter microservice communication
     * 
     * @param String token singed with private key
     * 
     * @return Claims all claims.
     */
    public Claims verifyServiceToken(String token) throws JwtException, IllegalArgumentException, Exception {
        Claims claims = Jwts.parser().verifyWith(getPublicKey()).build().parseSignedClaims(token).getPayload();
        return claims;
    }

    /*
     * Get service id from token if valid
     * 
     * @param String token sign wiht private key
     * 
     * @return String of service id
     */

    public String getServiceId(String token) throws JwtException, IllegalArgumentException, Exception {
        Claims claims = verifyServiceToken(token);
        return claims.getSubject();
    }
    /*
     * Extract claims and very token
     * 
     */

    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(generateSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    /*
     * 
     * extract user id form the given code.
     * 
     */
    public UUID generateUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        String userIdString = claims.getSubject(); // Assuming you're storing userId in subject
        try {
            return UUID.fromString(userIdString);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid UUID format in token");
        }
    }

}
