package com.feature.neighbourHood_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.feature.neighbourHood_backend.filter.jwtAuthFilter;
import com.feature.neighbourHood_backend.service.UserService;
import com.feature.neighbourHood_backend.util.jwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final jwtUtil jwtUtil;
    private final UserService userService;

    public SecurityConfig(jwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // 禁用 CSRF（API 不需要）
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 不使用 session，用 JWT
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login", "/api/register", "/db-test").permitAll()  // 這些不需要認證
                .anyRequest().authenticated()  // 其他請求需要認證
            )
            .addFilterBefore(new jwtAuthFilter(jwtUtil, userService), 
                UsernamePasswordAuthenticationFilter.class);  // 加入 JWT filter
        
        return http.build();
    }
}
