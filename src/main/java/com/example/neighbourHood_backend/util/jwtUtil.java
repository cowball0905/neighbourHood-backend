package com.example.neighbourHood_backend.util;

import com.example.neighbourHood_backend.Model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

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
}
