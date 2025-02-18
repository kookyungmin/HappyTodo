package net.happytodo.core.security.service;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;

@Service
@Slf4j
public class JwtService {
    public static String BEARER = "Bearer ";
    private static SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;
    @Value("${jwt.atk-expired-time}")
    private long atkExpiredTime;
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String createAccessToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("exp", Instant.now().getEpochSecond() + atkExpiredTime)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), algorithm)
                .compact();
    }

    public String verifyToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
