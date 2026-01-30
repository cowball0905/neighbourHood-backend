package com.feature.neighbourHood_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feature.neighbourHood_backend.model.CustomUserDetails;
import com.feature.neighbourHood_backend.model.DTO.ApiResponse;
import com.feature.neighbourHood_backend.model.entity.User;
import com.feature.neighbourHood_backend.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    public final UserService userService;

    public AdminController(UserService userService){
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
}