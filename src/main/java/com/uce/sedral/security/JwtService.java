package com.uce.sedral.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Getter
    @Value("${jwt.expiration:3600}")
    private Long jwtExpiration;

    @Getter
    @Value("${jwt.issuer:sedral-backend}")
    private String jwtIssuer;

    public String generateToken(Authentication authentication) {
        try {
            Instant now = Instant.now();
            var roles = authentication.getAuthorities().stream()
                    .map(a -> a.getAuthority().replace("ROLE_", ""))
                    .collect(Collectors.toList());

            System.out.println("Roles para JWT: " + roles);

            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            String token = Jwts.builder()
                    .setSubject(authentication.getName())
                    .setIssuer(jwtIssuer)
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plusSeconds(jwtExpiration)))
                    .claim("roles", roles)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
            return token;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("JWT generation failed", e);
        }
    }
}
