package com.example.neighbourHood_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // 禁用 CSRF（API 不需要）
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login", "/api/register", "/db-test").permitAll()  // 这些不需要认证
                .anyRequest().permitAll()  // 暂时允许所有请求（开发阶段）
            );
        
        return http.build();
    }
}
