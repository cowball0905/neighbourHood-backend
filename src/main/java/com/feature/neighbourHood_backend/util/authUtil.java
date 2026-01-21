package com.feature.neighbourHood_backend.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class authUtil {
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static boolean matchesPassword(String rPassword, String ePassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rPassword, ePassword);
    }
}
