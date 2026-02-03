package com.app.feature.notifications.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.app.feature.notifications.model.Notification;
import com.app.feature.notifications.repository.NotificationRepository;
import com.app.feature.post.model.PostEntity;

import jakarta.transaction.Transactional;

@Transactional 
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate){
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendLikeNotifications(PostEntity post){
        messagingTemplate.convertAndSend("/topic/post/" + post.getId(),post);
    }
}
