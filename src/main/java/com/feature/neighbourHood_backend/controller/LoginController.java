package com.feature.neighbourHood_backend.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feature.neighbourHood_backend.model.CustomUserDetails;
import com.feature.neighbourHood_backend.model.DTO.ApiResponse;
import com.feature.neighbourHood_backend.model.DTO.LoginRequestDTO;
import com.feature.neighbourHood_backend.model.DTO.RegisterRequestDTO;
import com.feature.neighbourHood_backend.service.UserService;
import com.feature.neighbourHood_backend.util.jwtUtil;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public LoginController(UserService userService, AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(dto.getEmail());
        String token = jwtUtil.createToken(userDetails);

        return new ApiResponse<>(true, token, "success");
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody RegisterRequestDTO request) {
        Boolean response = userService.register(request.getUsername(), request.getEmail(), request.getPassword());

        if (response) {
            return new ApiResponse<>(true, "success");
        } else {
            return new ApiResponse<>(false, "Email is registered");
        }
    }
}
