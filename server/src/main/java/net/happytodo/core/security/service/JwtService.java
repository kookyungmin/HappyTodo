package net.happytodo.core.security.service;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import net.happytodo.core.security.dto.JwtDTO;
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
    @Value("${jwt.rtk-expired-time}")
    private long rtkExpiredTime;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String createAccessToken(Authentication authentication) {
        return createToken(authentication.getName(), atkExpiredTime);
    }

    public String createRefreshToken(Authentication authentication) {
        return createToken(authentication.getName(), rtkExpiredTime);
    }

    public String createAccessToken(String subject) {
        return createToken(subject, atkExpiredTime);
    }

    public String createRefreshToken(String subject) {
        return createToken(subject, rtkExpiredTime);
    }

    private String createToken(String subject, long expiredTime) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("exp", Instant.now().getEpochSecond() + expiredTime)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), algorithm)
                .compact();
    }

    public JwtDTO verifyToken(String token) {
        try {
            String subject = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return JwtDTO.builder()
                    .isValidToken(true)
                    .subject(subject)
                    .build();
        } catch (Exception e) {
            return JwtDTO.builder()
                    .isValidToken(false)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }

    public long getRtkExpiredTime() {
        return this.rtkExpiredTime;
    }
}
