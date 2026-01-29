package com.feature.neighbourHood_backend.util;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.feature.neighbourHood_backend.model.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class jwtUtil {
    private static long time = 1000 * 60 * 60 * 24;
    private static final String SECRET = "ifhw9hcaos9hcap9chasp9chaochachdaochach9padol";
    private static Key SECRET_KEY = new SecretKeySpec(SECRET.getBytes(), 0, SECRET.getBytes().length, "HmacSHA256");

    public static String createToken(CustomUserDetails user) {
        String jwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject(user.getUsername())
                .claim("userID", user.getUuid())
                .claim("userEmail", user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .setId(UUID.randomUUID().toString())
                .compact();

        return jwtToken;
    }

    public Claims parseToken(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jwt) // token
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = parseToken(token);
        String tokenEmail = claims.get("userEmail", String.class);
        String userEmail = ((CustomUserDetails) userDetails).getEmail();
        return (tokenEmail.equals(userEmail) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
}
