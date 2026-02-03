package com.app.feature.notifications.service;

import org.springframework.stereotype.Service;

import com.app.feature.notifications.model.Notification;
import com.app.feature.notifications.repository.NotificationRepository;

@Service
public class NotificationService {
    public final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }


}
