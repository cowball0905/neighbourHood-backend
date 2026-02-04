package com.app.feature.notifications.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ApiResponse;
import com.app.feature.auth.model.CustomUserDetails;
import com.app.feature.notifications.model.Notification;
import com.app.feature.notifications.service.NotificationService;

@RestController
@RequestMapping("/api/noti")
@PreAuthorize("isAuthenticated()")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/")
    public ApiResponse<List<Notification>> getUserNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return new ApiResponse<>(true, notificationService.getAllNotifications(userDetails.getUser()), "");
    }
}
