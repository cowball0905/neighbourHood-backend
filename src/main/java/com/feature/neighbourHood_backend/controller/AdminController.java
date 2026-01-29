package com.feature.neighbourHood_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feature.neighbourHood_backend.model.DTO.ApiResponse;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // 只有 ADMIN 角色才能访问
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/info")
    public ApiResponse<String> adminInfo() {
        return new ApiResponse(true, "管理员专属信息");
    }

    // 任意登录用户都能访问
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public ApiResponse<String> userInfo() {
        return new ApiResponse(true, "普通用户也能看到");
    }
}