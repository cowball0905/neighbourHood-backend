package com.app.feature.auth.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ApiResponse;
import com.app.feature.auth.model.CustomUserDetails;
import com.app.feature.auth.model.User;
import com.app.feature.auth.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    public final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // 只有 ADMIN 角色才能访问
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/info")
    public ApiResponse<String> adminInfo() {
        return new ApiResponse(true, "");
    }

    // 任意登录用户都能访问
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public ApiResponse<String> userInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getUser(userDetails.getEmail());
        return new ApiResponse(true, user, "");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{id}")
    public ApiResponse<String> getUserInfoById(@AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID id) {
        User user = userService.findById(id);
        return new ApiResponse(true, user, "");
    }
}