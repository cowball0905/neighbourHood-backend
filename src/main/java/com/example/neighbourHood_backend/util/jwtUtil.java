package com.example.neighbourHood_backend.util;

import org.springframework.stereotype.Component;

import com.example.neighbourHood_backend.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Component
public class jwtUtil {
    private static long time = 1000*60*60*24;
    private static final String SECRET = "ifhw9hcaos9hcap9chasp9chaochachdaochach9padol";
    private static Key SECRET_KEY = new SecretKeySpec(SECRET.getBytes(), 0, SECRET.getBytes().length, "HmacSHA256");

    public static String createToken(User user){
        String jwtToken = Jwts.builder()
            .setHeaderParam("typ","JWT")
            .setHeaderParam("alg", "HS256")
            .claim("username", user.getName())
            .setExpiration(new Date(System.currentTimeMillis() + time))
            .signWith(SECRET_KEY,SignatureAlgorithm.HS256)
            .setId(UUID.randomUUID().toString())
            .compact();

        return jwtToken;
    }

    public Claims parseToken(String jwt){
        return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt) //token
                    .getBody();
    }

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims = parseToken(token);
        return claimResolver.apply(claims);
    }
}
